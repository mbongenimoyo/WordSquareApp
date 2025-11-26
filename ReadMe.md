# Word Square Coding Challenge Solution

## Challenge Overview

This repository contains my solution to the Word Square coding challenge. The objective is to arrange a given set of letters into an n x n grid so that every row and every column forms a valid English word, and the word in the ith row is the same as the word in the ith column.

### Problem Statement (from the spec)
> In a word square you are given a grid with letters arranged that spell valid English language words when you read from left to right or from top to bottom, with the requirement that the words you spell in each column and row of the same number are the same word. For example, the first row and the first column spell the same word, the second row and second column do, too, and so on. The challenge is that in arranging those letters that you spell valid words that meet those requirements.

Given:
- An integer n (grid size)
- n² letters to populate the grid

**Example Input:**
```
4 eeeeddoonnnsssrv
```
**Example Output:**
```
rose
oven
send
ends
```

## How to Use

### Build
This is a Maven-based Java project. You need Java 17+ and Maven installed.
```sh
mvn clean package
```

### Run - Interactive Mode (Recommended)
The application now runs as an interactive service, allowing you to solve multiple word squares without restarting:

```sh
mvn exec:java -Dexec.mainClass="org.moyo.WordSquareApp"
```

Once started, you can use the following commands:

#### Available Commands
- `solve -size <n> -sequence <letters>` - Solve a word square
- `load <path>` - Load a different dictionary file
- `help` - Display help information
- `exit` or `quit` - Exit the application

#### Interactive Example Session
```sh
> solve -size 4 -sequence eeeeddoonnnsssrv
Solving word square of size 4 with sequence: eeeeddoonnnsssrv

Found 1 solution(s):

rose
oven
send
ends

...done in 45ms

> solve -size 5 -sequence aabbeeeeeeeehmosrrrruttvv
Solving word square of size 5 with sequence: aabbeeeeeeeehmosrrrruttvv
...

> load ./custom-dictionary.txt
Loading dictionary from: ./custom-dictionary.txt
Dictionary loaded successfully.

> exit
Exiting Word Square Solver. Goodbye!
```

### Optional: Specify Initial Dictionary
You can optionally specify a dictionary file when starting the application:
```sh
mvn exec:java -Dexec.mainClass="org.moyo.WordSquareApp" -Dexec.args="-dictionaryFilePath ./custom-dictionary.txt"
```

## Challenge Test Cases
You can try the following challenge inputs in interactive mode:
```
solve -size 4 -sequence aaccdeeeemmnnnoo
solve -size 5 -sequence aaaeeeefhhmoonssrrrrttttw
solve -size 5 -sequence aabbeeeeeeeehmosrrrruttvv
solve -size 7 -sequence aaaaaaaaabbeeeeeeedddddggmmlloooonnssssrrrruvvyyy
```

## Dictionary
The default dictionary is based on [enable1.txt](http://norvig.com/ngrams/enable1.txt) as suggested in the spec. You can substitute your own dictionary using the `load` command or by specifying `-dictionaryFilePath` at startup.

## Approach & Design
- **Object-Oriented Design:** The solution is modular, with clear separation of concerns:
    - `WordSquareApp`: Handles interactive CLI, command parsing, and application lifecycle.
    - `DictionaryLoader`: Loads and manages the dictionary.
    - `WordSquareSolver`: Contains the core solving algorithm.
    - `WordSquareBuilder`: Supports construction of word squares.
- **Interactive Service Model:** The application runs continuously, allowing multiple solve operations without reloading the dictionary.
- **No 2D Arrays:** The implementation avoids 2D arrays, favoring more maintainable data structures.
- **APIs & Libraries:** Uses Java standard libraries and Google Guava for utility functions.
- **TDD:** The project includes unit tests for all major components (see `src/test`).

## Proof of Working Solution
- The repository contains unit tests for the solver and loader.
- Example outputs for the provided challenge inputs are included as comments in the test files.
- Run the program with the example inputs to see valid word squares produced.

## Development Notes
- The code is well-commented and organized for clarity.
- The interactive mode provides better performance for multiple solves since the dictionary is loaded only once.
- If you have any questions about design decisions or want to see more test cases, feel free to ask in the interview!

---

Thank you for reviewing my solution. I enjoyed tackling this challenge and am happy to discuss my approach, design, and testing strategy further.

## 🧩 Main Components

- **src/main/java/org/moyo/**
    - `WordSquareApp.java`: Interactive service application—run this to solve word squares!
    - `DefaultWordSquareBuilder.java`: Builds word squares from your chosen dictionary.
    - `DictionaryLoader.java`: Loads words from a text file.
    - `WordSquareSolver.java`: The logic engine for finding solutions.
- **src/main/resources/**: Place any dictionary or resource files here.
- **src/test/java/org/moyo/**: Contains all unit tests.
- **src/test/resources/**: Test-specific resources.

## ⚙️ How to Configure & Run the Application

The main entry point is `WordSquareApp`, which runs as an interactive service.

### Starting the Application
```sh
# Using the default dictionary
mvn exec:java -Dexec.mainClass="org.moyo.WordSquareApp"

# Using a custom dictionary at startup
mvn exec:java -Dexec.mainClass="org.moyo.WordSquareApp" -Dexec.args="-dictionaryFilePath ./custom-dictionary.txt"
```

### Interactive Commands

Once the application is running, use these commands:

**solve** - Solve a word square
```
solve -size <size> -sequence <letters>
```
- `-size <size>`: The size of the word square (must be an integer ≥ 2)
- `-sequence <letters>`: The sequence of letters to use (e.g., `aabbeeeeeeeehmosrrrruttvv`)

**load** - Load a different dictionary
```
load <path>
```

**help** - Display available commands

**exit** or **quit** - Exit the application

### Example Interactive Session
```sh
> solve -size 5 -sequence aabbeeeeeeeehmosrrrruttvv
> solve -size 4 -sequence eeeeddoonnnsssrv
> load ./another-dictionary.txt
> solve -size 3 -sequence abcdefghi
> exit
```

## 🛠️ Build & Test

Make sure you have Java 17+ and Maven installed.

To build:
```sh
mvn clean package
```

To test:
```sh
mvn test
```

## 📦 Dependencies
- [JUnit Jupiter 5.8.1](https://junit.org/junit5/) (testing)

## 💡 Notes & Tips
- All main logic is under `src/main/java/org/moyo/`.
- All tests are under `src/test/java/org/moyo/`.
- The interactive mode is more efficient for testing multiple word squares since the dictionary is loaded only once.
- Update or swap out the dictionary file using the `load` command to experiment with different word sets!

---

Enjoy puzzling! If you have questions, ideas, or want to contribute, feel free to open an issue or PR. Happy coding! 🚀