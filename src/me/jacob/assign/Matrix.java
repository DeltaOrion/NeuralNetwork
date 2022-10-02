package me.jacob.assign;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Function;

public class Matrix {

    private final double[][] values;

    public Matrix(int rows, int columns) {
        this.values = new double[rows][columns];
    }

    protected Matrix(double[][] array) {
        this.values = array;
    }

    public Matrix randomize() {
        Random random = new Random();
        Matrix matrix = new Matrix(getRows(),getColumns());
        for(int row=0;row<getRows();row++) {
            for(int col=0;col<getColumns();col++) {
                matrix.set(row,col,random.nextDouble());
            }
        }
        return matrix;
    }

    public Matrix add(Matrix matrix) {
        if(matrix.getRows()!=getRows())
            throw new IllegalArgumentException();

        if(matrix.getColumns()!=getColumns())
            throw new IllegalArgumentException();

        Matrix result = new Matrix(getRows(),getColumns());
        for(int row=0;row<getRows();row++) {
            for(int col=0;col<getColumns();col++) {
                result.set(row,col,matrix.get(row,col)+get(row,col));
            }
        }

        return result;
    }

    public Matrix subtract(Matrix matrix) {
        if(matrix.getRows()!=getRows())
            throw new IllegalArgumentException();

        if(matrix.getColumns()!=getColumns())
            throw new IllegalArgumentException();

        Matrix result = new Matrix(getRows(),getColumns());
        for(int row=0;row<getRows();row++) {
            for(int col=0;col<getColumns();col++) {
                result.set(row,col,get(row,col)-matrix.get(row,col));
            }
        }

        return result;
    }

    public Matrix transpose() {
        Matrix result = new Matrix(this.getColumns(),this.getRows());
        for(int row=0;row<getRows();row++) {
            for(int col=0;col<getColumns();col++) {
                result.set(col,row,get(row,col));
            }
        }
        return result;
    }

    public Matrix multiplyElementWise(Matrix matrix) {
        if(matrix.getRows()!=getRows())
            throw new IllegalArgumentException();

        if(matrix.getColumns()!=getColumns())
            throw new IllegalArgumentException();

        Matrix result = new Matrix(getRows(),getColumns());
        for(int row=0;row<getRows();row++) {
            for(int col=0;col<getColumns();col++) {
                result.set(row,col,matrix.get(row,col)*get(row,col));
            }
        }

        return result;
    }

    public Matrix multiply(Matrix matrix) {
        if(getColumns()!=matrix.getRows())
            throw new IllegalArgumentException();

        Matrix result = new Matrix(getRows(),matrix.getColumns());
        for(int i=0;i<getRows();i++) {
            for(int j=0;j<matrix.getColumns();j++) {
                double dotProduct = 0;
                for(int k=0;k<matrix.getRows();k++) {
                    dotProduct += this.get(i,k)*matrix.get(k,j);
                }
                result.set(i,j,dotProduct);
            }
        }

        return result;
    }

    public Matrix multiply(double scalar) {
        return map(input -> input*scalar);
    }

    public Matrix map(Function<Double,Double> mappingFunction) {
        Matrix matrix = new Matrix(getRows(),getColumns());
        for(int row=0;row<getRows();row++) {
            for(int col=0;col<getColumns();col++) {
                matrix.set(row,col,mappingFunction.apply(get(row,col)));
            }
        }
        return matrix;
    }

    public void set(int row, int col, double value) {
        values[row][col] = value;
    }

    public double get(int row, int col) {
        return values[row][col];
    }

    public double[][] toArray() {
        return values;
    }

    public static Matrix fromArray(double[][] array) {
        return new Matrix(array);
    }

    public static Matrix fromArray(double[] array, boolean row) {
        if(row)
            return fromArrayRow(array);

        return fromArrayCol(array);
    }

    private static Matrix fromArrayCol(double[] array) {
        Matrix matrix = new Matrix(array.length,1);
        for(int i=0;i<array.length;i++) {
            matrix.set(i,0,array[i]);
        }
        return matrix;
    }

    private static Matrix fromArrayRow(double[] array) {
        Matrix matrix = new Matrix(1,array.length);
        for(int i=0;i<array.length;i++) {
            matrix.set(0,i,array[i]);
        }

        return matrix;
    }

    public int getRows() {
        return values.length;
    }

    public int getColumns() {
        if(values.length==0)
            return 0;

        return values[0].length;
    }

    public Matrix clone() {
        Matrix matrix = new Matrix(getRows(),getColumns());
        for(int i=0;i<getRows();i++) {
            for(int j=0;j<getColumns();j++) {
                matrix.set(i,j,get(i,j));
            }
        }

        return matrix;
    }

    @Override
    public String toString() {
        return "Matrix{" +
                "values=" + Arrays.deepToString(values) +
                '}';
    }
}
