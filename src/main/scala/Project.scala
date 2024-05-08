package Labs

import java.io.{File, FileOutputStream, PrintWriter}
import scala.io.{BufferedSource, Source}
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.time.LocalDateTime
import java.text.SimpleDateFormat
import java.time.ZoneId
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.sql.{Connection, DriverManager, PreparedStatement}

object Project extends App {
  // Read data from the CSV file
  val source: BufferedSource = Source.fromFile("C:/Users/Mostafa Mohamed/IdeaProjects/Project/TRX1000.csv")
  val lines: List[String] = source.getLines().drop(1).toList // drop header

  // Initialize the logger
  val logger: Logger = LogManager.getLogger(getClass.getName)

  // Define the top2_avg_disc method
  def top2_avg_disc(order_record: List[String]): Double = {
    // Define IsQualified methods to check each rule
    // Rule 1: Check if there are less than 30 days remaining for the product to expire
    def IsQualified1(purchasedate: String, expirationdate: String): Boolean = {
      // Parse dates and calculate remaining days
      // Log that rule 1 is being checked
      val format = new SimpleDateFormat("yyyy-MM-dd")
      val pdate = format.parse(purchasedate)
      val edate = format.parse(expirationdate)
      val pdateLocalDate = pdate.toInstant.atZone(ZoneId.systemDefault()).toLocalDate()
      val edateLocalDate = edate.toInstant.atZone(ZoneId.systemDefault()).toLocalDate()
      logger.info("Checking rule 1...")
      val remainingdays = ChronoUnit.DAYS.between(pdateLocalDate, edateLocalDate)
      remainingdays < 30
    }

    // Rule 2: Check if the product name starts with "cheese" or "wine"
    def IsQualified2(product_name: String): Boolean = {
      // Log that rule 2 is being checked
      logger.info("Checking rule 2...")
      product_name.toLowerCase.startsWith("cheese") || product_name.toLowerCase.startsWith("wine")
    }

    // Rule 3: Check if the purchase date is March 23rd
    def IsQualified3(purchasedate: String): Boolean = {
      // Extract date parts and check if it's March 23rd
      // Log that rule 3 is being checked
      val datePart = purchasedate.substring(0, 10)
      val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
      val pdate = LocalDate.parse(datePart, formatter)
      val month = pdate.getMonthValue
      val day = pdate.getDayOfMonth
      logger.info("Checking rule 3...")
      month == 3 && day == 23
    }

    // Rule 4: Check if units sold are greater than 5
    def IsQualified4(units_sold: String): Boolean = {
      // Log that rule 4 is being checked
      logger.info("Checking rule 4...")
      units_sold.toInt > 5
    }

    // Rule 5: Check if the channel is "App"
    def IsQualified5(channel: String): Boolean = {
      // Log that rule 5 is being checked
      logger.info("Checking rule 5...")
      channel.trim.toLowerCase == "app"
    }

    // Rule 6: Check if the payment method is "Visa"
    def IsQualified6(Pay_method: String): Boolean = {
      // Log that rule 6 is being checked
      logger.info("Checking rule 6...")
      Pay_method.trim.toLowerCase == "visa"
    }

    // Calculate discount for each rule
    val c1 = order_record(0)
    val c2 = order_record(1)
    val c3 = order_record(2)
    val c4 = order_record(3)
    val c5 = order_record(4)
    val c6 = order_record(5)
    val c7 = order_record(6)

    val discounts_list = List(
      calc1(IsQualified1, c1, c3),
      calc2(IsQualified2, c2),
      calc3(IsQualified3, c1),
      calc4(IsQualified4, c4),
      calc5(IsQualified5, c6, c5),
      calc6(IsQualified6, c7)
    )
    // Calculate the top 2 average discount
    if (discounts_list.size >= 2) discounts_list.sorted.reverse.take(2).sum / 2.toDouble
    else if (discounts_list.size == 1) discounts_list.head
    else 0
  }

  // Define the get_price_disc method
  def get_price_disc(top2_avg_disc: List[String] => Double, order_record: List[String]): List[Double] = {
    // Calculate total discount and new price
    // Log that the price after discount is being calculated
    logger.info("Calculating total discount and new price...")
    val discount = top2_avg_disc(order_record)
    val price = order_record(4).toDouble * order_record(3).toDouble
    val final_price = price * (1 - discount)
    List(discount, final_price)
  }

  // Calculate discount for rule 1
  def calc1(IsQualified1: (String, String) => Boolean, purchasedate: String, expirationdate: String): Double = {
    if (IsQualified1(purchasedate, expirationdate)) {
      // Parse dates and calculate remaining days
      // Log that discount for rule 1 is being calculated
      val format = new SimpleDateFormat("yyyy-MM-dd")
      val pdate = format.parse(purchasedate)
      val edate = format.parse(expirationdate)
      val pdateLocalDate = pdate.toInstant.atZone(ZoneId.systemDefault()).toLocalDate()
      val edateLocalDate = edate.toInstant.atZone(ZoneId.systemDefault()).toLocalDate()
      val remainingdays = ChronoUnit.DAYS.between(pdateLocalDate, edateLocalDate)
      logger.info("Calculating discount for rule 1...")
      if (remainingdays < 30) {
        val discountPercentage = 30 - remainingdays
        discountPercentage.toDouble / 100.0
      } else {
        0.0
      }
    } else {
      0.0
    }
  }

  // Calculate discount for rule 2
  def calc2(IsQualified2: String => Boolean, productName: String): Double = {
    if (IsQualified2(productName)) {
      // Log that discount for rule 2 is being calculated
      logger.info("Calculating discount for rule 2...")
      val productLower = productName.toLowerCase
      if (productLower.startsWith("cheese")) {
        0.10
      } else if (productLower.startsWith("wine")) {
        0.05
      } else {
        0.0
      }
    } else {
      0.0
    }
  }

  // Calculate discount for rule 3
  def calc3(IsQualified3: String => Boolean, purchasedate: String): Double = {
    // Log that discount for rule 3 is being calculated
    logger.info("Calculating discount for rule 3...")
    if (IsQualified3(purchasedate)) 0.5
    else 0.0
  }

  // Calculate discount for rule 4
  def calc4(IsQualified4: String => Boolean, unitsSold: String): Double = {
    // Log that discount for rule 4 is being calculated
    logger.info("Calculating discount for rule 4...")
    if (IsQualified4(unitsSold.toString)) {
      unitsSold.toInt match {
        case q if q < 6 => 0.0
        case q if q < 10 => 0.05
        case q if q < 15 => 0.07
        case _ => 0.10
      }
    } else {
      0.0
    }
  }

  // Calculate discount for rule 5
  def calc5(IsQualified5: String => Boolean, channel: String, quantity: String): Double = {
    // Log that discount for rule 5 is being calculated
    logger.info("Calculating discount for rule 5...")
    if (IsQualified5(channel)) {
      val roundedQuantity = (math.ceil(quantity.toDouble / 5) * 5.0).toInt
      roundedQuantity match {
        case q if q <= 5 => 0.05
        case q if q <= 10 => 0.10
        case q if q <= 15 => 0.15
        case _ => 0.20
      }
    } else {
      0.0
    }
  }

  // Calculate discount for rule 6
  def calc6(IsQualified6: String => Boolean, channel: String): Double = {
    // Log that discount for rule 6 is being calculated
    logger.info("Calculating discount for rule 6...")
    if (IsQualified6(channel)) 0.05
    else 0.0
  }

  // Map each line of data to a target list with discount and final price
  val target_list: List[List[Double]] = lines.map { line =>
    val colist = line.split(",").toList
    logger.info("Mapping records...")
    get_price_disc(top2_avg_disc, colist)
  }

  // Define database connection parameters
  val url = "jdbc:mysql://localhost:3306/northwind"
  val user = "root"
  val password = "root"

  // Define SQL statement to insert data into the table
  val sql = "INSERT INTO discount (discount, price_after_discount) VALUES (?, ?)"

  // Establish a connection to the database
  var connection: Connection = null
  var preparedStatement: PreparedStatement = null

  try {
    connection = DriverManager.getConnection(url, user, password)
    preparedStatement = connection.prepareStatement(sql)

    // Iterate over the target list and insert each row into the database
    target_list.foreach { x =>
      val discount = x.head
      val priceAfterDiscount = x(1)

      // Set the parameters of the prepared statement
      preparedStatement.setDouble(1, discount)
      preparedStatement.setDouble(2, priceAfterDiscount)

      // Execute the SQL statement
      preparedStatement.executeUpdate()
    }

    println("Data inserted successfully into the table.")
  } catch {
    case e: Exception => e.printStackTrace()
  } finally {
    // Close the prepared statement and connection
    if (preparedStatement != null) preparedStatement.close()
    if (connection != null) connection.close()
  }
}
