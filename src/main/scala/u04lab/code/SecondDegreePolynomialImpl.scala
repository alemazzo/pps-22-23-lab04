package u04lab.code

case class SecondDegreePolynomialImpl(
                                       override val constant: Double,
                                       override val firstDegree: Double,
                                       override val secondDegree: Double
                                     ) extends SecondDegreePolynomial:
  override def +(polynomial: SecondDegreePolynomial): SecondDegreePolynomial =
    SecondDegreePolynomial(
      this.constant + polynomial.constant,
      this.firstDegree + polynomial.firstDegree,
      this.secondDegree + polynomial.secondDegree
    )

  override def -(polynomial: SecondDegreePolynomial): SecondDegreePolynomial =
    SecondDegreePolynomial(
      this.constant - polynomial.constant,
      this.firstDegree - polynomial.firstDegree,
      this.secondDegree - polynomial.secondDegree
    )
