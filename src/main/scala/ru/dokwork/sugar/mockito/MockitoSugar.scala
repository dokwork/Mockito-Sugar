package ru.dokwork.sugar.mockito

/**
  * Provides syntax sugar for [[http://site.mockito.org/ Mockito project]].
  *
  * @see [[SyntaxSugar]]
  * @see [[Answers]]
  * @see [[ShouldHaveMatcher]]
  */
trait MockitoSugar extends SyntaxSugar with Answers with ShouldHaveMatcher
