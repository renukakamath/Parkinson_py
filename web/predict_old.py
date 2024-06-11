
import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import MinMaxScaler
from xgboost import XGBClassifier
from sklearn.metrics import accuracy_score

import numpy as np
import pandas as pd

from sklearn.ensemble import RandomForestClassifier
from sklearn.neighbors import KNeighborsClassifier
from sklearn.metrics import accuracy_score
import joblib

def train():
	df = pd.read_csv('parkinsons.data')

	features = df.loc[:, df.columns != 'status'].values[:, 1:]
	labels = df.loc[:, 'status'].values

	print(features)
	print(labels)


	x_train, x_test, y_train, y_test = train_test_split(features, labels, test_size=0.2, random_state=0)

	scaler = MinMaxScaler((-1, 1))
	x_train = scaler.fit_transform(x_train)
	x_test = scaler.transform(x_test)

	xgb_model = XGBClassifier()
	xgb_model.fit(x_train, y_train)

	# Save the XGBoost model to a file
	joblib.dump(xgb_model, 'xgb_model.pkl')


	rf_model = RandomForestClassifier(n_estimators=100, random_state=0)
	rf_model.fit(x_train, y_train)
	# Save the Random Forest model to a file
	joblib.dump(rf_model, 'rf_model.pkl')

	knn_model = KNeighborsClassifier(n_neighbors=5)
	knn_model.fit(x_train, y_train)
	# Save the KNN model to a file
	joblib.dump(knn_model, 'knn_model.pkl')



	y_pred = xgb_model.predict(x_test)
	accuracy = accuracy_score(y_test, y_pred) * 100
	print("Accuracy:", accuracy)

	rf_pred = rf_model.predict(x_test)
	rf_acc = accuracy_score(y_test, rf_pred) * 100
	print("Random Forest Accuracy:", rf_acc)

	knn_pred = knn_model.predict(x_test)
	knn_acc = accuracy_score(y_test, knn_pred) * 100
	print("KNN Accuracy:", knn_acc)


def predict(val):

	# print(val)
	# val=[[119.99200,157.30200,74.99700,0.00784,0.00007,0.00370,0.00554,0.01109,0.04374,0.42600,0.02182,0.03130,0.02971,0.06545,0.02211,21.03300,0.414783,0.815285,-4.813031,0.266482,2.301442,0.284654]]
	# Load the Random Forest model from the file
	loaded_rf_model = joblib.load('rf_model.pkl')

	# Make predictions using the loaded Random Forest model
	loaded_rf_model_pred = loaded_rf_model.predict(val)

	print("out :", loaded_rf_model_pred)

	# Load the KNN model from the file
	loaded_knn_model = joblib.load('knn_model.pkl')

	# Make predictions using the loaded KNN model
	# loaded_knn_model_pred = loaded_knn_model.predict(val)

	# print("out :", loaded_knn_model_pred)

	# Load the XGBoost model from the file
	# loaded_xgb_model = joblib.load('xgb_model.pkl')

	# # Make predictions using the loaded XGBoost model
	# loaded_xgb_model_pred = loaded_xgb_model.predict(val)


	# print("out:", loaded_xgb_model_pred)
	return loaded_rf_model_pred



# train()