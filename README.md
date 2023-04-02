# Neural Network 

The following program is a basic forward feeding multiplayer perceptron. A perceptron is divided up into an input layer, hidden layers and output layer. The neural network can either feedforward, which allows it to return an output with the given inputs, which is approximated with its current weights and biases or back propogate which takes an input, expected output and improves its approximator function. 

![Image of a multilayer perceptron](multi-layer-perceptron-in-tensorflow.png)

The neural network only supports a sigmoid activation function and a meaned square error function. This program was made without the help of any external libraries. 

## Building 

To build the program, run the following commands

Windows
```cmd
mkdir out
javac -d out src/**/*.java
```

Bash
```sh
mkdir out
find src -name "*.java" -exec javac -d out {} +
```

## Running

Once executed you can either run the ColorPicker example or the XOR example

```sh
# Color Example
java -cp out me.jacob.assign.ColorSelector

# XOR Example
java -cp out me.jacob.assign.XORProblem
```