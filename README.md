# Custom Programming Language README

## Overview
This document provides an overview of the syntax and structure of the custom programming language.

## Comments
- Multi-line comments are enclosed within `##`.
  ```
  ##
  This is a multi-line comment.
  ##
  ```
- Single-line comments start with `$$`.
  ```
  $$ This is a single-line comment.
  ```

## Variables
- Global variables start with `:`.
- Supported data types:
  - `flag` (boolean)
  - `whole` (integer)
  - `point` (floating point)
  - `letter` (character)

Example:
```
flag :global = hn ;
whole :count = 10 ;
point :pi = 3.14159 ;
letter :alpha = 'a' ;
```

## Operators
The language supports the following arithmetic operations:
```
result = value1 + value2 ;  $$ Addition
result = value1 - value2 ;  $$ Subtraction
result = value1 * value2 ;  $$ Multiplication
result = value1 / value2 ;  $$ Division
result = value1 % value2 ;  $$ Modulus
result = value1 ^ value2 ;  $$ Power (Exponentiation)
```
Example:
```
point exponentResult = 2.5 ^ 3.0 ;  $$ 2.5^3 = 15.625
```

## Printing to Console
Use the `print` statement to output text.
```
print(Hello Hi) ;
```

## Syntax Rules
- All keywords and identifiers must be in **lowercase**.
- Every statement must end with a **semicolon (`;`)**.

## Regular Expressions
```
char = a+b+c+d+e+f+g+h+i+j+k+l+m+n+o+p+q+r+s+t+u+v+w+x+y+z

digit = 1+2+3+4+5+6+7+8+9+0

datatype = flag + whole + point + letter

identifier = letter letter*
global_identifier = : identifier

operator = '+' + '-' + '*' + '/' + '%' + '^' + '='

integer = digit digit*
float = digit digit* . digit{1,5}
char = 'letter'
boolean = hn + na

single line Comment $$
multilinecomment = ## ##

print_statement = print((letter + digit)*)

terminator = ;
```

