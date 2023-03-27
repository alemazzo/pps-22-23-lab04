package u04lab.code

import org.junit.Assert.{assertEquals, assertTrue}
import org.junit.{Assert, Test}

class SecondDegreePolynomialTest:

  val secondDegreePolynomial = SecondDegreePolynomial(1, 2, 1)

  @Test def testSum() =
    val first = SecondDegreePolynomial(1, 2, 1)
    val second = SecondDegreePolynomial(1, 2, 1)
    val sum = first + second
    val expected = SecondDegreePolynomial(2, 4, 2)
    assertEquals(expected, sum)


  @Test def testSubtract() =
    val first = SecondDegreePolynomial(1, 2, 1)
    val second = SecondDegreePolynomial(1, 2, 1)
    val subtract = first - second
    val expected = SecondDegreePolynomial(0, 0, 0)
    assertEquals(expected, subtract)



