Skala
=====
[![Build Status](https://travis-ci.org/Kevin-Lee/skala.svg)](https://travis-ci.org/Kevin-Lee/skala)

Utilities for Scala

##skala.math

### skala.math.BigInts
#### abs()
An absolute value of `BigInt`

```scala
import cc.kevinlee.skala.math.BigInts._

abs(BigInt(-1))
// Result: BigInt(1)

abs(BigInt(1))
// Result: BigInt(1)
```

#### sqrt()
The square root of a BigInt value

```scala
import cc.kevinlee.skala.math.BigInts._

sqrt(BigInt(9))
// Result: BigDecimal(3)

sqrt(BigInt(10))
// Result: BigDecimal(3.162277660168379331998893544432718)
```


### skala.math.BigDecimals
#### abs()
An absolute value of `BigDecimal`

```scala
import cc.kevinlee.skala.math.BigDecimal._

abs(BigDecimal(-1))
// Result: BigDecimal(1)

abs(BigDecimal(1))
// Result: BigDecimal(1)
```

### sqrt()
The square root of a BigDecimal value

```scala
import cc.kevinlee.skala.math.BigInts._

sqrt(BigDecimal(9))
// Result: BigDecimal(3)

sqrt(BigDecimal(10))
// Result: BigDecimal(3.162277660168379331998893544432718)
```