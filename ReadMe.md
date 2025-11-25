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

### Run
The main entry point is `WordSquareApp`. Run it from the command line:
```sh
mvn exec:java -Dexec.mainClass="org.moyo.WordSquareApp" -Dexec.args="-size <n> -sequence <letters> [-dictionaryFilePath <path>]"
```
- `-size <n>`: Size of the square (e.g., 4 or 5)
- `-sequence <letters>`: The n² letters to use (e.g., `eeeeddoonnnsssrv`)
- `-dictionaryFilePath <path>`: (Optional) Path to dictionary file (default: `src/main/resources/words.txt`)

### Example
```sh
mvn exec:java -Dexec.mainClass="org.moyo.WordSquareApp" -Dexec.args="-size 4 -sequence eeeeddoonnnsssrv"
```

## Challenge Test Cases
You can try the following challenge inputs:
```
4 aaccdeeeemmnnnoo
5 aaaeeeefhhmoonssrrrrttttw
5 aabbeeeeeeeehmosrrrruttvv
7 aaaaaaaaabbeeeeeeedddddggmmlloooonnssssrrrruvvyyy
```

## Dictionary
The default dictionary is based on [enable1.txt](http://norvig.com/ngrams/enable1.txt) as suggested in the spec. You can substitute your own dictionary if desired.

## Approach & Design
- **Object-Oriented Design:** The solution is modular, with clear separation of concerns:
  - `WordSquareApp`: Handles CLI and argument parsing.
  - `DictionaryLoader`: Loads and manages the dictionary.
  - `WordSquareSolver`: Contains the core solving algorithm.
  - `WordSquareBuilder`: Supports construction of word squares.
- **No 2D Arrays:** The implementation avoids 2D arrays, favoring more maintainable data structures.
- **APIs & Libraries:** Uses Java standard libraries and Google Guava for utility functions.
- **TDD:** The project includes unit tests for all major components (see `src/test`).

## Proof of Working Solution
- The repository contains unit tests for the solver and loader.
- Example outputs for the provided challenge inputs are included as comments in the test files.
- Run the program with the example inputs to see valid word squares produced.

## Development Notes
- The code is well-commented and organized for clarity.
- If you have any questions about design decisions or want to see more test cases, feel free to ask in the interview!

---

Thank you for reviewing my solution. I enjoyed tackling this challenge and am happy to discuss my approach, design, and testing strategy further.

## 🧩 Main Components

- **src/main/java/org/moyo/**
  - `WordSquareApp.java`: The heart of the application—run this to solve word squares!
  - `DefaultWordSquareBuilder.java`: Builds word squares from your chosen dictionary.
  - `DictionaryLoader.java`: Loads words from a text file.
  - `WordSquareSolver.java`: The logic engine for finding solutions.
- **src/main/resources/**: Place any dictionary or resource files here.
- **src/test/java/org/moyo/**: Contains all unit tests.
- **src/test/resources/**: Test-specific resources.

## ⚙️ How to Configure & Run the Application

The main entry point is `WordSquareApp`. You can configure the application using command-line arguments:

### Required Arguments
- `-size <size>`: The size of the word square (must be an integer ≥ 2).
- `-sequence <letters>`: The sequence of letters to use (e.g., `aabbeeeeeeeehmosrrrruttvv`).

### Optional Arguments
- `-dictionaryFilePath <path>`: Path to your dictionary file (default: `src/main/resources/words.txt`).

### Example Usage
```sh
# Using the default dictionary
mvn exec:java -Dexec.mainClass="org.moyo.WordSquareApp" -Dexec.args="-size 5 -sequence aabbeeeeeeeehmosrrrruttvv"

# Using a custom dictionary
mvn exec:java -Dexec.mainClass="org.moyo.WordSquareApp" -Dexec.args="-size 5 -sequence aabbeeeeeeeehmosrrrruttvv -dictionaryFilePath ./custom-dictionary.txt"
```

If you forget the required arguments, the app will show usage instructions and examples.

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
- Update or swap out the dictionary file to experiment with different word sets!

---

Enjoy puzzling! If you have questions, ideas, or want to contribute, feel free to open an issue or PR. Happy coding! 🚀
