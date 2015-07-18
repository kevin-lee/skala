package cc.kevinlee.skala.math

/**
 * @author Lee, Seong Hyun (Kevin)
 * @since 2015-07-18
 */
object CommonMath {
  def mode[T](numbers: Seq[T])(implicit cmp: Ordering[T]): Seq[T] =
    if (numbers.isEmpty)
      Nil
    else {
      val grouped = numbers.groupBy(identity)
                           .map { case (n, ns) => (n, ns.length) }
                           .groupBy(_._2)
      val max = grouped.maxBy(_._1)._1
      grouped(max).keySet.toList.sorted
    }

}
