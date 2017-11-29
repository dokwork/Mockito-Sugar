package ru.dokwork.test.sugar

import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.Answer
import org.mockito.{
  ArgumentCaptor,
  ArgumentMatcher,
  ArgumentMatchers,
  MockSettings,
  Mockito ⇒ Mock
}

/**
  * Provides syntax sugar for methods from [[http://site.mockito.org/ Mockito project]].
  *
  * @example Instead use [[org.mockito.Mockito#mock(java.lang.Class) mock method]] directly like:
  *          {{{  org.mockito.Mockito.mock(classOf[MyClass])          }}}
  *          you can use method from this trait:
  *          {{{  mock[MyClass]                                       }}}
  */
trait Mockito {

  /**
    * This equals to: {{{  org.mockito.Mockito.mock(classOf[T])  }}}
    */
  def mock[T <: AnyRef: Manifest] = {
    Mock.mock(clazz)
  }

  /**
    * This equals to: {{{  org.mockito.Mockito.mock(classOf[T], name)  }}}
    */
  def mock[T <: AnyRef: Manifest](name: String) = {
    Mock.mock(clazz, name)
  }

  /**
    * This equals to: {{{  org.mockito.Mockito.mock(classOf[T], defaultAnswer)  }}}
    */
  def mock[T <: AnyRef: Manifest](defaultAnswer: Answer[_]) = {
    Mock.mock(clazz, defaultAnswer)
  }

  /**
    * This equals to: {{{  org.mockito.Mockito.mock(classOf[T], mockSettings)  }}}
    */
  def mock[T <: AnyRef: Manifest](mockSettings: MockSettings) = {
    Mock.mock(clazz, mockSettings)
  }

  /**
    * This equals to: {{{  org.mockito.ArgumentMatchers#any(classOf[T])  }}}
    */
  def any[T: Manifest]: T = {
    ArgumentMatchers.any(clazz)
  }

  /**
    * This equals to: {{{  org.mockito.ArgumentMatchers#any(classOf[T])  }}}
    */
  def *[T: Manifest]: T = {
    ArgumentMatchers.any(clazz)
  }

  /**
    * This equals to: {{{  org.mockito.ArgumentMatchers#argThat(new ArgumentMatcher[T] {
    *  override def matches(argument: T) = f(argument)
    * })  }}}
    */
  def ![T: Manifest](f: (T) ⇒ Boolean) = {
    ArgumentMatchers.argThat(new ArgumentMatcher[T] {
      override def matches(argument: T) = f(argument)
    })
  }

  /**
    * This equals to: {{{  org.mockito.ArgumentCaptor.forClass(classOf[T])  }}}
    */
  def captor[T: Manifest]: ArgumentCaptor[T] = ArgumentCaptor.forClass(clazz)

  private def clazz[T: Manifest]: Class[T] = manifest.runtimeClass.asInstanceOf[Class[T]]
}

object Mockito {

  /**
    * Provides implicit conversion functions to [[org.mockito.stubbing.Answer]].
    * Arguments for functions will be arguments of the method which answer used for.
    *
    * @example {{{
    *   val list: List[Int] = mock[List[Int]]
    *   doAnswer((n: Int) ⇒ (1 to n).toList).when(list).take(*[Int])
    * }}}
    */
  trait Answers {

    implicit def Answer0[T](f: () ⇒ T) = new Answer[T] {
      override def answer(invocation: InvocationOnMock) = f.apply()
    }

    implicit def Answer1[A, T](f: (A) ⇒ T) = new Answer[T] {
      override def answer(i: InvocationOnMock) = f.apply(i.getArguments()(0).asInstanceOf[A])
    }

    implicit def Answer2[A, B, T](f: (A, B) ⇒ T) = new Answer[T] {
      override def answer(i: InvocationOnMock) = f.apply(
        i.getArguments()(0).asInstanceOf[A],
        i.getArguments()(1).asInstanceOf[B]
      )
    }

    implicit def Answer3[A, B, C, T](f: (A, B, C) ⇒ T) = new Answer[T] {
      override def answer(i: InvocationOnMock) = f.apply(
        i.getArguments()(0).asInstanceOf[A],
        i.getArguments()(1).asInstanceOf[B],
        i.getArguments()(2).asInstanceOf[C]
      )
    }
  }
}
