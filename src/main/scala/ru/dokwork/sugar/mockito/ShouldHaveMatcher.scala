package ru.dokwork.sugar.mockito

import org.mockito.Mockito.{ times, verify }
import org.mockito.verification.VerificationMode
import org.scalatest.Assertion

/**
 * Provide ability to wrap mocks verification to the [[org.scalatest.Assertion]].
 *
 * ''It may be useful for property-based tests with trait [[org.scalatest.prop.PropertyChecks]].''
 *
 * @example {{{
 *  // given:
 *  val list: List[Int] = mock[List[Int]]
 *  // when:
 *  list.take(5)
 *  // then:
 *  list shouldHave invocation(_.take(*[Int]), atLeast(1))
 * }}}
 */
trait ShouldHaveMatcher {

  case class MockInvocation[T](mode: VerificationMode, f: (T) ⇒ Unit)

  def invocation[T](f: (T) ⇒ Unit, mode: VerificationMode = times(1)) = MockInvocation(mode, f)

  implicit class MockShouldWrapper[T](val mock: T) {

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
    def shouldHave(invocation: MockInvocation[T]): Assertion = new Assertion {
      invocation.f(verify(mock, invocation.mode))
    }
  }
}
