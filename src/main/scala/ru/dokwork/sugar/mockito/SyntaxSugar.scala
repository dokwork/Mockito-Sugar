package ru.dokwork.sugar.mockito

import org.mockito._
import org.mockito.stubbing.Answer

/**
 * Provides syntax sugar for methods:
 *
 * - [[org.mockito.Mockito#mock(java.lang.Class) mock]]
 * - [[org.mockito.ArgumentMatchers#any(java.lang.Class) any]]
 * - [[org.mockito.ArgumentMatchers#argThat(org.mockito.ArgumentMatcher) argThat]]
 *
 * and method for create [[org.mockito.ArgumentCaptor]]
 *
 * @example Instead use [[org.mockito.Mockito#mock(java.lang.Class) mock method]] directly like:
 *          {{{  org.mockito.Mockito.mock(classOf[MyClass])          }}}
 *          you can use method from this trait:
 *          {{{  mock[MyClass]                                       }}}
 */
trait SyntaxSugar {

  /**
   * This equals to: {{{  org.mockito.Mockito.mock(classOf[T])  }}}
   */
  def mock[T <: AnyRef : Manifest]: T = {
    Mockito.mock(clazz)
  }

  /**
   * This equals to: {{{  org.mockito.Mockito.mock(classOf[T], name)  }}}
   */
  def mock[T <: AnyRef : Manifest](name: String): T = {
    Mockito.mock(clazz, name)
  }

  /**
   * This equals to: {{{  org.mockito.Mockito.mock(classOf[T], defaultAnswer)  }}}
   */
  def mock[T <: AnyRef : Manifest](defaultAnswer: Answer[_]): T = {
    Mockito.mock(clazz, defaultAnswer)
  }

  /**
   * This equals to: {{{  org.mockito.Mockito.mock(classOf[T], mockSettings)  }}}
   */
  def mock[T <: AnyRef : Manifest](mockSettings: MockSettings): T = {
    Mockito.mock(clazz, mockSettings)
  }

  /**
   * This equals to: {{{  org.mockito.ArgumentMatchers#any(classOf[T])  }}}
   */
  def any[T: Manifest]: T = {
    ArgumentMatchers.any(clazz)
  }

  private def clazz[T: Manifest]: Class[T] = manifest.runtimeClass.asInstanceOf[Class[T]]

  /**
   * This equals to: {{{  org.mockito.ArgumentMatchers#argThat(new ArgumentMatcher[T] {
   *  override def matches(argument: T) = f(argument)
   * })  }}}
   */
  def argThat[T](f: (T) ⇒ Boolean): T = {
    ArgumentMatchers.argThat(new ArgumentMatcher[T] {
      override def matches(argument: T) = f(argument)
    })
  }
}
