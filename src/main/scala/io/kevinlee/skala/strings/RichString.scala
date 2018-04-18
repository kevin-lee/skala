package io.kevinlee.skala.strings

/**
  * @author Kevin Lee
  * @since 2017-05-06
  */
object RichString {
  /**
    * Uncapitalize String.
    * {{{
    *   e.g.)
    *   RichString.uncapitalize("Test")
    *   // "test"
    *
    *   RichString.uncapitalize("TestSomething")
    *   // "testSomething"
    *
    *   RichString.uncapitalize("")
    *   // ""
    *
    *   val a: String = null
    *   RichString.uncapitalize(a)
    *   // java.lang.NullPointerException
    * }}}
    * @param value
    * @return
    */
  def uncapitalize(value: String): String =
    if (value.isEmpty) value
    else value.head.toLower.toString + value.tail

  implicit class ToUncapitalize(val value: String) extends AnyVal {
    /**
      * Uncapitalize String
      * {{{
      *   e.g.)
      *   "Test".uncapitalize
      *   // "test"
      *
      *   "TestSomething".uncapitalize
      *   // "testSomething"
      *
      *   "".uncapitalize
      *   // ""
      *
      *   val a: String = null
      *   a.uncapitalize
      *   // java.lang.NullPointerException
      * }}}
      * @return
      */
    def uncapitalize: String = RichString.uncapitalize(value)
  }
}
