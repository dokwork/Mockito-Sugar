package ru.dokwork.sugar.mockito

import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.Answer

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

  implicit def Answer4[A, B, C, D, T](f: (A, B, C, D) ⇒ T) = new Answer[T] {
    override def answer(i: InvocationOnMock) = f.apply(
      i.getArguments()(0).asInstanceOf[A],
      i.getArguments()(1).asInstanceOf[B],
      i.getArguments()(2).asInstanceOf[C],
      i.getArguments()(3).asInstanceOf[D]
    )
  }
}
