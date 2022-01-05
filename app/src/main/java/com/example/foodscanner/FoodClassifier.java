package com.example.foodscanner;

import android.content.Context;

import com.example.foodscanner.Product;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.*;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class FoodClassifier {


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
    ArrayList<Attribute> healthinessAttributes = new ArrayList<Attribute>();
    NaiveBayes foodtypeClassifier = new NaiveBayes();
    NaiveBayes healthinessClassifier = new NaiveBayes();
    int classIndex1;
    int classIndex2;


    public FoodClassifier(Context context) throws Exception {
        trainModels(context);
    }

    public String predictFoodType(com.example.foodscanner.Product product) throws Exception {
        //Predict food type
        Instances testingSet1 = new Instances("Testing Set",foodTypeAttributes,1);

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

        System.out.println("Predicted Food type: " + predictedFoodType);
        return predictedFoodType;
    }


    public String predictHealthiness(com.example.foodscanner.Product product) throws Exception {
        //Predict healthiness
        Instances testingSet2 = new Instances("Testing Set",healthinessAttributes,1);

        String predictedFoodType = predictFoodType(product);
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

        Instance unlabeled = testingSet2.instance(0);
        double myValue = healthinessClassifier.classifyInstance(unlabeled);
        String predictedHealthiness = testingSet2.classAttribute().value((int) myValue);
        System.out.println("Predicted healthiness: " + predictedHealthiness);
        return predictedHealthiness;
    }

    private void trainModels(Context context) throws Exception {


        InputStream is = context.getResources().openRawResource(R.raw.trainfoodtype);
        BufferedReader datafile = new BufferedReader(new InputStreamReader(is));
        //food type classifier
//        ConverterUtils.DataSource source1 = new ConverterUtils.DataSource("C:/trainfoodtype.arff");
        Instances trainingSet1 = new Instances(datafile);
        classIndex1 = trainingSet1.numAttributes() - 1;
        trainingSet1.setClassIndex(classIndex1);

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

        foodtypeClassifier.buildClassifier(trainingSet1);


        //healthiness classifier
        InputStream is1 = context.getResources().openRawResource(R.raw.trainhealthiness);
        BufferedReader datafile1 = new BufferedReader(new InputStreamReader(is1));
        Instances trainingSet2 = new Instances(datafile1);
        classIndex2 = trainingSet2.numAttributes() - 1;
        trainingSet2.setClassIndex(classIndex2);

        healthinessAttributes.add(type);
        healthinessAttributes.add(carbs);
        healthinessAttributes.add(fat);
        healthinessAttributes.add(protein);
        healthinessAttributes.add(fiber);
        healthinessAttributes.add(sodium);
        healthinessAttributes.add(saturatedfat);
        healthinessAttributes.add(healthiness);

        healthinessClassifier.buildClassifier(trainingSet2);

    }

//    public static void main(String[] args) throws Exception {
//        // load data
//        Product product = new Product("https://world.openfoodfacts.org/api/v0/product/8718906821934.json");
//        FoodClassifier foodClassifier = new FoodClassifier();
//        System.out.println(foodClassifier.predictHealthiness(product));
//    }

}
