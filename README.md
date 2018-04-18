Skala
=====
[![Build Status](https://semaphoreci.com/api/v1/projects/0749a644-9deb-49a2-8781-da758bc731be/439826/badge.svg)](https://semaphoreci.com/kevin-lee/skala)
[![Build Status](https://semaphoreci.com/api/v1/projects/0749a644-9deb-49a2-8781-da758bc731be/439826/shields_badge.svg)](https://semaphoreci.com/kevin-lee/skala)
[![Download](https://api.bintray.com/packages/kevinlee/maven/skala/images/download.svg)](https://bintray.com/kevinlee/maven/skala/_latestVersion)

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/6918a01879ce4870b7210f0f1c0bef18)](https://www.codacy.com/app/kevin-lee/skala?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Kevin-Lee/skala&amp;utm_campaign=Badge_Grade)
[![Coverage Status](https://coveralls.io/repos/Kevin-Lee/skala/badge.svg)](https://coveralls.io/r/Kevin-Lee/skala)

[![Build Status](https://travis-ci.org/Kevin-Lee/skala.svg)](https://travis-ci.org/Kevin-Lee/skala)


Utilities for Scala

## skala.math

### skala.math.BigInts

#### sqrt()
The square root of a BigInt value

```scala
import io.kevinlee.skala.math.BigInts._

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
import io.kevinlee.skala.math.BigInts._

findSqrt(BigInt(9))
// Result: Some(BigDecimal(3))

findSqrt(BigInt(10))
// Result: Some(BigDecimal(3.162277660168379331998893544432718))


findSqrt(BigInt(-1))
// Result: None
```

#### mean()
It calculates a mean of `TraversableLike[BigInt, TraversableLike[BigInt, _]]`.

```scala
import io.kevinlee.skala.math.BigInts._

val numbers = List[BigInt](1, 2, 3)
mean(numbers)  // returns BigDecimal(2)
```

#### median()
It finds a median of `Seq[BigInt]`.

```scala
import io.kevinlee.skala.math.BigInts._

val numbers = List[BigInt](1, 2, 3, 4)
median(numbers)  // return BigDecimal(2.5)
```

```scala
import io.kevinlee.skala.math.BigInts._

val numbers = List[BigInt](2, 4, 3, 1)
median(numbers)  // return BigDecimal(2.5)
```

```scala
import io.kevinlee.skala.math.BigInts._

val numbers = List[BigInt](1, 2, 3, 4, 5)
median(numbers)  // return BigDecimal(3)
```

```scala
import io.kevinlee.skala.math.BigInts._

val numbers = List[BigInt](2, 3, 5, 4, 1)
median(numbers)  // return BigDecimal(3)
```


### skala.math.BigDecimals

### sqrt()
The square root of a BigDecimal value

```scala
import io.kevinlee.skala.math.BigDecimals._

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
import io.kevinlee.skala.math.BigDecimals._

findSqrt(BigDecimal(9))
// Result: Some(BigDecimal(3))

findSqrt(BigDecimal(10))
// Result: Some(BigDecimal(3.162277660168379331998893544432718))


findSqrt(BigDecimal(-1))
// Result: None
```

#### mean()
It calculates a mean of `TraversableLike[BigDecimal, TraversableLike[BigDecimal, _]]`.

```scala
import io.kevinlee.skala.math.BigDecimals._

val numbers = List[BigDecimal](1, 2, 3)
mean(numbers)  // returns BigDecimal(2)
```

#### median()
It finds a median of `Seq[BigDecimal]`.

```scala
import io.kevinlee.skala.math.BigDecimals._

val numbers = List[BigDecimal](1, 2, 3, 4)
median(numbers)  // return BigDecimal(2.5)
```

```scala
import io.kevinlee.skala.math.BigDecimals._

val numbers = List[BigDecimal](2, 4, 3, 1)
median(numbers)  // return BigDecimal(2.5)
```

```scala
import io.kevinlee.skala.math.BigDecimals._

val numbers = List[BigDecimal](1, 2, 3, 4, 5)
median(numbers)  // return BigDecimal(3)
```

```scala
import io.kevinlee.skala.math.BigDecimals._

val numbers = List[BigDecimal](2, 3, 5, 4, 1)
median(numbers)  // return BigDecimal(3)
```

## Try with Resource
### tryWith

```scala
import io.kevinlee.skala.util.TryWith.SideEffect.tryWith
```

```scala
tryWith(AutoCloseable) { autoCloseable =>
  // run block
}
```
**`tryWith` is similar to Java's Try with Resource so it may throw an exception if the block or `AutoCloseable` throws any exception.**

```scala
val result: WhatEverTheRunBlockReturns = tryWith(AutoCloseable) { autoCloseable =>
  // run block
}
```

```scala
tryWith(new FileInputStream("/path/to/file.txt")) { inputStream =>
  var c = inputStream.read()
  while (c != -1) {
    print(c.asInstanceOf[Char])
    c = inputStream.read()
  }
}
```

```scala
tryWith(new FileInputStream("/path/to/file.txt")) { inputStream =>
  tryWith(new InputStreamReader(inputStream)) { reader =>
    var c = reader.read()
    while (c != -1) {
      print(c.asInstanceOf[Char])
      c = reader.read()
    }
  }
}
```

```scala
val result = tryWith(new SomeResource()) { someSource =>
  someSource.get
}
```

### TryWith

```scala
import io.kevinlee.skala.util.TryWith
```

```scala
TryWith(AutoCloseable) { autoCloseable =>
  // run block
}
```
**`TryWith` returns `Try` type after apply the given function then closes the `AutoCloseable`.**

```scala
val result: Try[WhatEverTheRunBlockReturns] = TryWith(AutoCloseable) { autoCloseable =>
  // run block
}
```

```scala
TryWith(new FileInputStream("/path/to/file.txt")) { inputStream =>
  var c = inputStream.read()
  while (c != -1) {
    print(c.asInstanceOf[Char])
    c = inputStream.read()
  }
}
```

```scala
TryWith(new FileInputStream("/path/to/file.txt")) { inputStream =>
  /* 
   * NOTE: The inner tryWith is tryWith not TryWith. 
   * Otherwise, it results in Try[Try[T]] instead of Try[T]
   * Try[Try[T]] case will be handled in a future release
   */
  tryWith(new InputStreamReader(inputStream)) { reader =>
    var c = reader.read()
    while (c != -1) {
      print(c.asInstanceOf[Char])
      c = reader.read()
    }
  }
}
```

```scala
val result: Try[String] = tryWith(new SomeResource()) { someSource =>
  someSource.get    // returns String 
}
```

# Get Skala

In your `build.sbt`, add the following repo and dependency.

**Note: It supports Scala 2.11 and 2.12.**

```scala
resolvers += "3rd Party Repo" at "http://dl.bintray.com/kevinlee/maven"

libraryDependencies += "io.kevinlee" %% "skala" % "0.0.10"
```
