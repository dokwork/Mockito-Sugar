package ru.dokwork.sugar.mockito

import org.mockito.Mockito.{ times, verify }
import org.mockito.verification.VerificationMode
import org.scalactic.{ Prettifier, source }
import org.scalatest.Assertion
import org.scalatest.exceptions.{ StackDepthException, TestFailedException }

import scala.util.{ Failure, Success, Try }

/**
  * Provide ability to wrap mocks verification to the [[org.scalatest.Assertion]].
  */
trait ShouldHaveMatcher {

  case class MockInvocation[T](f: (T) ⇒ Unit, mode: VerificationMode)

  /**
    * Creates verification of method invocation to check it with
    * [[ru.dokwork.sugar.mockito.ShouldHaveMatcher.ShouldVerifyWrapper#shouldHave(ru.dokwork.sugar.mockito.ShouldHaveMatcher.MockInvocation) shouldHave]]
    * word.
    *
    * @param f function to invoke verified method on the mock.
    * @param mode see [[org.mockito.verification.VerificationMode VerificationMode]].
    *             Default is `times(1)`.
    * @tparam T type of the mock.
    */
  def invocation[T](f: (T) ⇒ Unit, mode: VerificationMode = times(1)): MockInvocation[T] = {
    MockInvocation(f, mode)
  }

  implicit class ShouldVerifyWrapper[T](val mock: T)(implicit pos: source.Position, p: Prettifier) {

    /**
      * @example {{{
      *  // given:
      *  val list: List[Int] = mock[List[Int]]
      *  // when:
      *  list.take(5)
      *  // then:
      *  list shouldHave invocation(_.take(*[Int]), atLeast(1))
      *  // equals to verify(list, atLeast(1)).take(*[Int]),
      *  // but returns Assert instead List
      * }}}
      */
    def shouldHave(invocation: MockInvocation[T]): Assertion = {
      Try(invocation.f(verify(mock, invocation.mode))) match {
        case Success(_) ⇒
          org.scalatest.Succeeded
        case Failure(e) ⇒
          val messageFun: StackDepthException ⇒ Option[String] = _ ⇒ Option(e.getMessage)
          throw new TestFailedException(messageFun, Some(e), pos)
      }
    }
  }
}
