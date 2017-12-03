package ru.dokwork.sugar.mockito

import org.mockito.Mockito.{ times, verify }
import org.mockito.verification.VerificationMode
import org.scalactic.{ Prettifier, source }
import org.scalatest.Assertion
import org.scalatest.exceptions.{ StackDepthException, TestFailedException }

import scala.util.{ Failure, Success, Try }

/**
  * Provide ability to wrap mocks verification to the [[org.scalatest.Assertion]].
  *
  * ''It may be useful for property-based tests with trait [[org.scalatest.prop.PropertyChecks]].''
  */
trait ShouldHaveMatcher {

  def invocation[T](f: (T) ⇒ Unit, mode: VerificationMode = times(1)): MockInvocation[T] = {
    MockInvocation(f, mode)
  }

  implicit class MockShouldWrapper[T](val mock: T)(implicit pos: source.Position, p: Prettifier) {

    /**
      * @example {{{
      *  // given:
      *  val list: List[Int] = mock[List[Int]]
      *  // when:
      *  list.take(5)
      *  // then:
      *  list shouldHave invocation(_.take(*[Int]), atLeast(1))
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

case class MockInvocation[T](f: (T) ⇒ Unit, mode: VerificationMode)
