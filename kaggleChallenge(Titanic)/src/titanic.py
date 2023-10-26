import pandas as pd
import numpy as np



def sigmoid(z):
    return 1.0 / (1 + np.exp(-z))

def predict(features, weights):
    z = np.dot(features, weights)
    return sigmoid(z)


def cost_function(features, labels, weights):

    predictions = predict(features, weights)

    class1_cost = -labels*np.log(predictions)
    class2_cost = (1-labels)*np.log(1-predictions)
    cost = class1_cost - class2_cost

    cost = cost.sum() / len(labels)

    return cost

def update_weights(features, labels, weights, lr):

    N = len(features)

    predictions = predict(features, weights)

    gradient = np.dot(features.T,  predictions - labels)
    gradient /= N
    gradient *= lr

    weights -= gradient

    return weights

def decision_boundary(prob):
    for i in range(len(prob)):
        prob[i] = 1 if prob[i] >= .5 else 0
    return prob

def classify(predictions):

    return decision_boundary(predictions).flatten()

def train(features, labels, weights, lr):
    cost_history = [1000]
    i = 0

    while True:
        i += 1
        weights = update_weights(features, labels, weights, lr)

        #Calculate error for auditing purposes
        cost = cost_function(features, labels, weights)
        cost_history.append(cost)

        # Log Progress
        if i % 1000 == 0:
            print( "iter: "+str(i) + " cost: "+str(cost))

        if cost_history[-2] - cost <= 0.001 or i >= 5000:
            print(i)
            break

    return weights, cost_history


def accuracy(predicted_labels, actual_labels):
    diff = predicted_labels - actual_labels
    return 1.0 - (float(np.count_nonzero(diff)) / len(diff))

def main():
    trainDF = pd.read_csv("Data/train.csv")
    testDF = pd.read_csv("Data/test.csv")

    workingDF = pd.DataFrame()
    sur = pd.DataFrame()
    sur = trainDF["Survived"]
    sur = sur.to_numpy()

    workingDF["Pclass"] = trainDF["Pclass"]
    workingDF["Sex"] = np.where(trainDF["Sex"] == "female", 1, 0)
    workingDF["SibSp"] = trainDF["SibSp"]
    workingDF["Parch"] = trainDF["Parch"]

    init_weights = np.array((1.0, 1.0, 1.0, 1.0))
    features = workingDF.to_numpy()

    weights, _ = train(features, sur,  init_weights, 0.2)
    predictions = predict(features, weights)
    print(accuracy(classify(predictions), sur))
    print(weights)

    testfeatures = pd.DataFrame()

    testfeatures["Pclass"] = testDF["Pclass"]
    testfeatures["Sex"] = np.where(testDF["Sex"] == "female", 1, 0)
    testfeatures["SibSp"] = testDF["SibSp"]
    testfeatures["Parch"] = testDF["Parch"]
    testfeatures = testfeatures.to_numpy()

    predictions = predict(testfeatures, weights)
    predictions = classify(predictions)
    predictionsDF = pd.DataFrame()
    predictionsDF["PassengerId"] = testDF["PassengerId"]
    predictionsDF["Survived"] = pd.DataFrame(predictions, columns=["Survived"], dtype="int64")

    predictionsDF.to_csv("Data/result.csv", index=False)








if __name__ == "__main__":
    main()