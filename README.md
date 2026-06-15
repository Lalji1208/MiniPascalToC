# Mini Pascal to C Transpiler

A compiler/transpiler written in Java for the Concepts of Programming Languages course.

## Features

Supported language constructs:

- Variable declarations
- Assignments
- Arithmetic expressions
- Input (read)
- Output (write)
- IF statements
- WHILE loops

## Architecture

Mini Pascal Source
↓
Lexer
↓
Parser
↓
AST
↓
Semantic Analyzer
↓
C Code Generator
↓
output.c

## Example

Mini Pascal:

```pascal
var x;

x := 10;

if x > 5 then
begin
    write(x);
end
```

Generated C:

```c
#include <stdio.h>

int main() {

    int x;

    x = 10;

    if(x > 5) {
        printf("%d\n", x);
    }

    return 0;
}
```

## Project Structure

src/
lexer/
parser/
ast/
semantic/
generator/

examples/
output/

## Error Handling

Compilation errors are written to:

output/error.invalid

Examples:

- Undeclared variables
- Duplicate declarations
- Syntax errors

## Author

Lalji Nasit