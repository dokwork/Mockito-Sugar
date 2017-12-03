package ru.dokwork.sugar.mockito

/**
  * Provides syntax sugar for [[http://site.mockito.org/ Mockito project]].
  *
  * @see [[SyntaxSugar]]
  * @see [[Answers]]
  * @see [[ShouldHaveMatcher]]
  * @see [[RichArgumentCaptor]]
  */
trait MockitoSugar extends SyntaxSugar with Answers with ShouldHaveMatcher with RichArgumentCaptor
