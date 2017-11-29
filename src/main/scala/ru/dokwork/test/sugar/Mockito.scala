package ru.dokwork.test.sugar

import org.mockito.stubbing.Answer
import org.mockito.{ArgumentCaptor, ArgumentMatcher, ArgumentMatchers, MockSettings, Mockito }

/**
  * Provides syntax sugar for methods from [[http://site.mockito.org/ Mockito project]].
  *
  * @example Instead use [[org.mockito.Mockito#mock(java.lang.Class) mock method]] directly like:
  *          {{{  org.mockito.Mockito.mock(classOf[MyClass])          }}}
  *          you can use method from this trait:
  *          {{{  mock[MyClass]                                       }}}
  */
trait Mockito extends Answers {

  /**
    * This equals to: {{{  org.mockito.Mockito.mock(classOf[T])  }}}
    */
  def mock[T <: AnyRef: Manifest] = {
    Mockito.mock(clazz)
  }

  /**
    * This equals to: {{{  org.mockito.Mockito.mock(classOf[T], name)  }}}
    */
  def mock[T <: AnyRef: Manifest](name: String) = {
    Mockito.mock(clazz, name)
  }

  /**
    * This equals to: {{{  org.mockito.Mockito.mock(classOf[T], defaultAnswer)  }}}
    */
  def mock[T <: AnyRef: Manifest](defaultAnswer: Answer[_]) = {
    Mockito.mock(clazz, defaultAnswer)
  }

  /**
    * This equals to: {{{  org.mockito.Mockito.mock(classOf[T], mockSettings)  }}}
    */
  def mock[T <: AnyRef: Manifest](mockSettings: MockSettings) = {
    Mockito.mock(clazz, mockSettings)
  }

  /**
    * This equals to: {{{  org.mockito.ArgumentMatchers#any(classOf[T])  }}}
    */
  def any[T: Manifest]: T = {
    ArgumentMatchers.any(clazz)
  }

  /**
    * This equals to [[ru.dokwork.test.sugar.Mockito#any(scala.reflect.Manifest)]]
    */
  def *[T: Manifest]: T = {
    ArgumentMatchers.any(clazz)
  }

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

  /**
    * This equals to [[ru.dokwork.test.sugar.Mockito#argThat(scala.Function1)]]
    */
  def ==[T](f: (T) ⇒ Boolean): T = argThat(f)

  /**
    * This equals to: {{{  org.mockito.ArgumentCaptor.forClass(classOf[T])  }}}
    */
  def argumentCaptor[T: Manifest]: ArgumentCaptor[T] = ArgumentCaptor.forClass(clazz)

  private def clazz[T: Manifest]: Class[T] = manifest.runtimeClass.asInstanceOf[Class[T]]
}
