Skala
=====
[![Build Status](https://semaphoreci.com/api/v1/projects/0749a644-9deb-49a2-8781-da758bc731be/439826/badge.svg)](https://semaphoreci.com/kevin-lee/skala)
[![Build Status](https://semaphoreci.com/api/v1/projects/0749a644-9deb-49a2-8781-da758bc731be/439826/shields_badge.svg)](https://semaphoreci.com/kevin-lee/skala)
[![Build Status](https://travis-ci.org/Kevin-Lee/skala.svg)](https://travis-ci.org/Kevin-Lee/skala)
[![Download](https://api.bintray.com/packages/kevinlee/maven/skala/images/download.svg)](https://bintray.com/kevinlee/maven/skala/_latestVersion)

[![Codacy Badge](https://www.codacy.com/project/badge/6918a01879ce4870b7210f0f1c0bef18)](https://www.codacy.com/app/kevin-lee/skala)
[![Coverage Status](https://coveralls.io/repos/Kevin-Lee/skala/badge.svg)](https://coveralls.io/r/Kevin-Lee/skala)
[![Issue Stats](http://www.issuestats.com/github/Kevin-Lee/skala/badge/issue)](http://www.issuestats.com/github/Kevin-Lee/skala)

[![views](https://sourcegraph.com/api/repos/github.com/Kevin-Lee/skala/.counters/views.svg)](https://sourcegraph.com/github.com/Kevin-Lee/skala)
[![views 24h](https://sourcegraph.com/api/repos/github.com/Kevin-Lee/skala/.counters/views-24h.svg)](https://sourcegraph.com/github.com/Kevin-Lee/skala)

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

```
tryWith(AutoCloseable) { autoCloseable =>
  // run block
}
```

```
val result = tryWith(AutoCloseable) { autoCloseable =>
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

# Get Skala

In your `build.sbt`, add the following repo and dependency.

**Note: It supports only Skala 2.11.x.**

```scala
resolvers += "3rd Party Repo" at "http://dl.bintray.com/kevinlee/maven"

libraryDependencies += "io.kevinlee" %% "skala" % "0.0.5"
```
