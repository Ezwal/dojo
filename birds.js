#!/usr/bin/env node

const idiot = a => a
const kestrel = a => b => a
const kite = a => b => b
const cardinal = f => a => b => f(b)(a)
const mockingbird = f => a => f(f(a))
const bluebird = f => g => a => f(g(a))
const thrush = a => f => f(a)
const blackbird = f => g => a => b => f(g(a)(b))

const True = idiot
const False = kite
const Not = cardinal(False)(idiot)
