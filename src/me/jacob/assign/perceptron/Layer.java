package me.jacob.assign.perceptron;

import me.jacob.assign.math.Matrix;

public class Layer {

    //id of the layer (for debugging
    private final int index;
    private final NeuralNetwork neuralNetwork;

    //amount of neurons in the layer
    private final int neurons;

    //next and previous layers
    //if there is no previous layer then this is an input layer
    private Layer previousLayer;
    private Layer nextLayer;

    //neurons
    private Matrix activations;
    private Matrix weights;
    private Matrix biases;

    //used for back propagation
    private Matrix error;
    private Matrix weightNudges;
    private Matrix biasNudges;
    private int trainingCount;

    public Layer(NeuralNetwork neuralNetwork, int index, int neurons) {
        this.index = index;
        this.neurons = neurons;
        this.activations = new Matrix(neurons, 1).randomize();
        this.neuralNetwork = neuralNetwork;
    }

    public void feedForward() {
        if (previousLayer != null) {
            Matrix z = weights.multiply(previousLayer.activations)
                    .add(biases);
            activations = z.map(this::sigmoid);
        }

        if (nextLayer != null)
            nextLayer.feedForward();
    }

    public void setNextLayer(Layer nextLayer) {
        this.nextLayer = nextLayer;
    }

    public void setPreviousLayer(Layer layer) {
        this.previousLayer = layer;
        weights = new Matrix(getNeuronCount(), previousLayer.getNeuronCount()).randomize();
        biases = new Matrix(getNeuronCount(), 1).randomize();

        weightNudges = new Matrix(weights.getRows(), weights.getColumns());
        biasNudges = new Matrix(biases.getRows(), biases.getColumns());
    }

    private double sigmoid(double x) {
        return 1 / (1 + Math.pow(Math.E, -x));
    }

    private double dSigmoidOut(double sigmoid) {
        return sigmoid * (1 - sigmoid);
    }

    public int getNeuronCount() {
        return neurons;
    }

    @Override
    public String toString() {
        return "Layer{" +
                "index=" + index +
                '}';
    }

    public void setValues(double[] values) {
        if (values.length != neurons)
            throw new IllegalArgumentException();

        for (int i = 0; i < values.length; i++) {
            activations.set(i, 0, values[i]);
        }
    }

    public double[] getValues() {
        double[] values = new double[neurons];
        for (int i = 0; i < values.length; i++) {
            values[i] = activations.get(i, 0);
        }
        return values;
    }

    public Matrix getActivations() {
        return activations.clone();
    }

    public void setError(Matrix error) {
        this.error = error;
    }

    public void propagateBackwards() {
        if (previousLayer == null)
            return;

        if (nextLayer != null) {
            Matrix nextLayerTranspose = nextLayer.weights.transpose();
            error = nextLayerTranspose.multiply(nextLayer.error);
        }

        Matrix gradients = activations.map(this::dSigmoidOut);
        gradients = gradients.multiplyElementWise(error)
                .multiply(neuralNetwork.getLearningRate());

        //add to biases
        biasNudges = biasNudges.add(gradients);

        //add to weights
        gradients = gradients.multiply(previousLayer.getActivations().transpose());
        weightNudges = weightNudges.add(gradients);

        trainingCount++;

        previousLayer.propagateBackwards();
    }

    public void applyChanges() {
        if(previousLayer==null)
            return;

        weightNudges.multiply(1.0/trainingCount);
        biasNudges.multiply(1.0/trainingCount);

        weights = this.weights.add(weightNudges);
        biases = this.biases.add(biasNudges);

        weightNudges = new Matrix(weights.getRows(), weightNudges.getColumns());
        biases = new Matrix(biasNudges.getRows(), biases.getColumns());
        trainingCount = 0;

        previousLayer.applyChanges();
    }

}
