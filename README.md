# scala-data-science
Combine breeze and saddle

 * `HWData.scala`: helper routines for loading the height-weight data from CSVs.
 * `LogisticRegression.scala`: Re-usable Scala class implementing logistic regression.
 * `LogisticRegressionDemo.scala`: Example run for the Logistic regression class.
 * `BreezeDemo.scala`: Example code for plotting simple x-y plots, scatter plots (including scatter plots with variable point size and color), as well as multiple plots on one figure.
 * `ScatterplotMatrix.scala` contains a class for plotting [scatterplot matrices](http://www.utexas.edu/courses/schwab/sw388r7/Tutorials/IllustrationofRegressionAnalysis_doc_html/024_The_Scatterplot_Matrix.html). Run `ScatterPlotMatrixDemo` to see this in action.


## Parallel collections

Some of the code examples rely on the same height-weight data that was presented in chapter 2.

 - `LogisticRegression.scala`: an extension of the `LogisticRegression` class written in chapter 2 that also contains `predictProbabilitiesMany` and `classifyMany` methods for generating predictions on a test set.
 - `HWData.scala`: routines for loading the height-weight data from CSVs.
 - `RandomSubsample.scala`: class to perform random subsample cross-validation in parallel using a parallel range.
 - `RandomSubsampleDemo.scala`: Demonstration program showing how to use the `RandomSubsample` class.

## Futures

 - `BlockDemo.scala`: simple program demonstrating how to block to await the completion of a future.
 - `StockPriceDemo.scala`: Simple command line application for fetching stock prices from the [Markit on demand](http://dev.markitondemand.com/MODApis/) API. Stock prices are fetched asynchronously to avoid blocking the user interface.

## Model classes

 - `Gender.scala`: a Scala enum to represent gender.
 - `Physicist.scala`: case class describing a physicist.

## Loan pattern and 'pimp my library' examples

 - `SqlUtils.scala`: Routines for opening a connection using the loan pattern and for wrapping a `ResultSet` instance in a Scala stream.
 - `RichConnection.scala`: class wrapping a JDBC connection to allow opening `ResultSet` instances using the loan pattern. This class should not be used explicitly. Instead, we provide implicit conversions from a JDBC `Connection` instance to an instance of `RichConnection` using the *pimp my library* pattern. These implicit conversions are provided in the `Implicits.scala` code file.

## Type classes

 - `SqlReader.scala`: abstract trait and specific implementations of a `SqlReader[T]` trait that exposes a `read` method describing how to extract an instance of type `T` from a field in a `ResultSet`.
 - `RichResultSet.scala`: class wrapping a JDBC `ResultSet` instance to offer a `read[T]` method that extracts an instance of `T` from a `ResultSet`. The `read[T]` method relies on the existence of a `SqlReader[T]` type class. The `RichResultSet` class should not be used explicitly. Rather, we define a conversion in `Implicits.scala` that implicitly adds the functionality in the `RichResultSet` class to a JDBC `ResultSet`.
 - `Implicits.scala`: Implicit conversions from a JDBC `Connection` to a `RichConnection` and back, and from a `ResultSet` to a `RichResultSet`.

## Data access object

Data access objects (DAOs) are objects that provide functions to serialize and deserialize specific classes to and from a SQL database. They assume the SQL table follows a specific schema.

 - `PhysicistDao.scala` provides a method for reading every row in a `physicists` SQL table, converting each row to an instance of the `Physicist` case class.
 - `PhysicistDaoDemo.scala` exercises the `PhysicistDao` class.