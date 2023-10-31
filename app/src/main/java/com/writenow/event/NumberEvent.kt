package com.writenow.event

class NumberSuccessEvent (val result: String)
class NumberErrorEvent (val case: String, val code: Int)