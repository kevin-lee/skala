Skala
=====
[![Build Status](https://travis-ci.org/Kevin-Lee/skala.svg)](https://travis-ci.org/Kevin-Lee/skala)

Utilities for Scala

## skala.math

### skala.math.BigInts

#### sqrt()
The square root of a BigInt value

```scala
import cc.kevinlee.skala.math.BigInts._

sqrt(BigInt(9))
// Result: BigDecimal(3)

sqrt(BigInt(10))
// Result: BigDecimal(3.162277660168379331998893544432718)


sqrt(BigInt(-1))
// Result: IllegalArgumentException
```

#### findSqrt()
It returns `Option` may or may not have a square root of a `BigInt` value. This method doesn't throw any exception when a negative number is passed as a parameter. Instead, it returns `None`.
```scala
import cc.kevinlee.skala.math.BigInts._

findSqrt(BigInt(9))
// Result: Some(BigDecimal(3))

findSqrt(BigInt(10))
// Result: Some(BigDecimal(3.162277660168379331998893544432718))


findSqrt(BigInt(-1))
// Result: None
```


### skala.math.BigDecimals

### sqrt()
The square root of a BigDecimal value

```scala
import cc.kevinlee.skala.math.BigDecimals._

sqrt(BigDecimal(9))
// Result: BigDecimal(3)

sqrt(BigDecimal(10))
// Result: BigDecimal(3.162277660168379331998893544432718)

sqrt(BigDecimal(-1))
// Result: IllegalArgumentException
```

#### findSqrt()
It returns `Option` may or may not have a square root of a `BigDecimal` value. This method doesn't throw any exception when a negative number is passed as a parameter. Instead, it returns `None`.
```scala
import cc.kevinlee.skala.math.BigDecimals._

findSqrt(BigDecimal(9))
// Result: Some(BigDecimal(3))

findSqrt(BigDecimal(10))
// Result: Some(BigDecimal(3.162277660168379331998893544432718))


findSqrt(BigDecimal(-1))
// Result: None
```
