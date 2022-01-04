package com.example.foodscanner;

import weka.classifiers.bayes.NaiveBayes;
import weka.core.*;
import weka.core.Instances;
import weka.classifiers.Evaluation;
import weka.core.converters.ConverterUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class FoodClassifier {

    public String predictHealthiness(Product product) throws Exception {

        //predict food type
        ConverterUtils.DataSource source1 = new ConverterUtils.DataSource("/app/src/main/java/com/example/foodscanner/trainFoodtype.arff");
        Instances trainingSet1 = source1.getDataSet();
        int classIndex1 = trainingSet1.numAttributes() - 1;
        trainingSet1.setClassIndex(classIndex1);

        String[] types = {"Bread","Softdrink","Cereals"};
        String[] classes = {"Unhealthy","Neutral","Healthy"};
        Attribute type = new Attribute("type", Arrays.asList(types));
        Attribute carbs = new Attribute("carbs");
        Attribute fat = new Attribute("fat");
        Attribute protein = new Attribute("protein");
        Attribute fiber = new Attribute("fiber");
        Attribute sodium = new Attribute("sodium");
        Attribute saturatedfat = new Attribute("saturatedfat");
        Attribute healthiness = new Attribute("healthiness",Arrays.asList(classes));
        Attribute salt = new Attribute("salt");
        Attribute energy = new Attribute("energy");
        Attribute sugar = new Attribute("sugar");

        ArrayList<Attribute> foodTypeAttributes = new ArrayList<Attribute>();
        foodTypeAttributes.add(carbs);
        foodTypeAttributes.add(sugar);
        foodTypeAttributes.add(fat);
        foodTypeAttributes.add(saturatedfat);
        foodTypeAttributes.add(salt);
        foodTypeAttributes.add(energy);
        foodTypeAttributes.add(sodium);
        foodTypeAttributes.add(protein);
        foodTypeAttributes.add(fiber);
        foodTypeAttributes.add(type);

        Instances testingSet1 = new Instances("Testing Set",foodTypeAttributes,1);
        NaiveBayes foodtypeClassifier = new NaiveBayes();
        foodtypeClassifier.buildClassifier(trainingSet1);

        Evaluation eval1 = new Evaluation(trainingSet1);
        eval1.crossValidateModel(foodtypeClassifier, trainingSet1, 10, new Random(1));
        System.out.println("-----Food type classifier-----");
        System.out.println(eval1.toSummaryString());
        System.out.println(eval1.toMatrixString());
        System.out.println(eval1.toClassDetailsString());

        DenseInstance productInstance1 = new DenseInstance(10);
        productInstance1.setValue(carbs, product.getCarbs());
        productInstance1.setValue(sugar, product.getSugar());
        productInstance1.setValue(fat, product.getFat());
        productInstance1.setValue(saturatedfat, product.getSaturatedFat());
        productInstance1.setValue(salt, product.getSalt());
        productInstance1.setValue(energy, product.getEnergy());
        productInstance1.setValue(sodium, product.getSodium());
        productInstance1.setValue(protein, product.getProteins());
        productInstance1.setValue(fiber,product.getFiber());
        productInstance1.setMissing(classIndex1);

        testingSet1.add(productInstance1);
        testingSet1.setClassIndex(classIndex1);

        Instance unlabeled = testingSet1.instance(0);
        double myValue = foodtypeClassifier.classifyInstance(unlabeled);
        String predictedFoodType = testingSet1.classAttribute().value((int) myValue);

        //predict healthines
        ConverterUtils.DataSource source2 = new ConverterUtils.DataSource("trainHealthiness.arff");
        Instances trainingSet2 = source2.getDataSet();
        int classIndex2 = trainingSet2.numAttributes() - 1;
        trainingSet2.setClassIndex(classIndex2);




        ArrayList<Attribute> healthinessAttributes = new ArrayList<Attribute>();
        healthinessAttributes.add(type);
        healthinessAttributes.add(carbs);
        healthinessAttributes.add(fat);
        healthinessAttributes.add(protein);
        healthinessAttributes.add(fiber);
        healthinessAttributes.add(sodium);
        healthinessAttributes.add(saturatedfat);
        healthinessAttributes.add(healthiness);

        Instances testingSet2 = new Instances("Testing Set",healthinessAttributes,9);
        NaiveBayes healthinessClassifier = new NaiveBayes();
        healthinessClassifier.buildClassifier(trainingSet2);

        Evaluation eval2 = new Evaluation(trainingSet2);
        eval2.crossValidateModel(foodtypeClassifier, trainingSet2, 10, new Random(1));
        System.out.println("-----Healthiness classifier-----");
        System.out.println(eval2.toSummaryString());
        System.out.println(eval2.toMatrixString());
        System.out.println(eval2.toClassDetailsString());

        DenseInstance productInstance2 = new DenseInstance(8);
        productInstance2.setValue(type, predictedFoodType);
        productInstance2.setValue(carbs, product.getCarbs());
        productInstance2.setValue(fat, product.getFat());
        productInstance2.setValue(protein, product.getProteins());
        productInstance2.setValue(fiber,product.getFiber());
        productInstance2.setValue(sodium, product.getSodium());
        productInstance2.setValue(saturatedfat, product.getSaturatedFat());
        productInstance2.setMissing(classIndex2);

        testingSet2.add(productInstance2);
        testingSet2.setClassIndex(classIndex2);

        unlabeled = testingSet2.instance(0);
        myValue = healthinessClassifier.classifyInstance(unlabeled);
        String predictedHealthiness = testingSet2.classAttribute().value((int) myValue);
        System.out.println("Predicted Food type: " + predictedFoodType);
        System.out.println("Predicted healthiness: " + predictedHealthiness);
        return predictedHealthiness;
    }


//    public static void main(String[] args) throws Exception {
//        // load data
//        Product product = new Product("https://world.openfoodfacts.org/api/v0/product/8718906821934.json");
//        FoodClassifier foodClassifier = new FoodClassifier();
//        System.out.println(foodClassifier.predictHealthiness(product));
//    }

}
