import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import MinMaxScaler
from sklearn.tree import DecisionTreeClassifier
from xgboost import XGBClassifier
from sklearn.ensemble import RandomForestClassifier
from sklearn.neighbors import KNeighborsClassifier
from sklearn.metrics import accuracy_score
import joblib

def train():
    df = pd.read_csv('parkinsons.data')

    selected_features = [
        'MDVP:Fo(Hz)', 'MDVP:Fhi(Hz)', 'MDVP:Flo(Hz)',
        'MDVP:Jitter(%)', 'MDVP:Jitter(Abs)', 'MDVP:RAP'
    ]

    features = df[selected_features].values
    print(features)
    labels = df['status'].values
    print(labels)

    x_train, x_test, y_train, y_test = train_test_split(features, labels, test_size=0.2, random_state=0)

    scaler = MinMaxScaler((-1, 1))
    x_train = scaler.fit_transform(x_train)
    x_test = scaler.transform(x_test)

    xgb_model = XGBClassifier()
    xgb_model.fit(x_train, y_train)
    joblib.dump(xgb_model, 'xgb_model_new.pkl')

    rf_model = RandomForestClassifier(n_estimators=100, random_state=0)
    rf_model.fit(x_train, y_train)
    joblib.dump(rf_model, 'rf_model_new.pkl')

    knn_model = KNeighborsClassifier(n_neighbors=5)
    knn_model.fit(x_train, y_train)
    joblib.dump(knn_model, 'knn_model_new.pkl')

    clf = DecisionTreeClassifier(max_depth=5)  # Create a DecisionTreeClassifier
    clf.fit(x_train, y_train)  # Train the DecisionTreeClassifier
    joblib.dump(clf, 'decision_tree_model_new.pkl')  # Save the DecisionTreeClassifier model

    y_pred = xgb_model.predict(x_test)
    accuracy = accuracy_score(y_test, y_pred) * 100
    print("XGBoost Accuracy:", accuracy)

    rf_pred = rf_model.predict(x_test)
    rf_acc = accuracy_score(y_test, rf_pred) * 100
    print("Random Forest Accuracy:", rf_acc)

    knn_pred = knn_model.predict(x_test)
    knn_acc = accuracy_score(y_test, knn_pred) * 100
    print("KNN Accuracy:", knn_acc)

    clf_pred = clf.predict(x_test)
    clf_acc = accuracy_score(y_test, clf_pred) * 100
    print("Decision Tree Accuracy:", clf_acc)

def predict(val):
    loaded_rf_model = joblib.load('rf_model_new.pkl')
    loaded_rf_model_pred = loaded_rf_model.predict(val)
    print(loaded_rf_model_pred)
    return loaded_rf_model_pred

# Call the train() function
train()
