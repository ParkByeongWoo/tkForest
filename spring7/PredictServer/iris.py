# package import
import pandas as pd
import pickle

from sklearn.datasets import load_iris
from sklearn.neighbors import KNeighborsClassifier
from sklearn.model_selection import train_test_split

# 데이터 불러오기
iris = load_iris()

# print(iris) # dict() 제공

# DataFrame로 만들기
iris_df = pd.DataFrame(iris['data'], columns=iris['feature_names'])
target  = iris['target_names']

# print(iris_df)
# print(target)

# x와 y 처리
X = iris_df         # 2차원 Matrix
y = iris['target']  # 1차원 Vector

# 섞어서 나누는 작업
X_train, X_test, y_train, y_test = train_test_split(X
                                                    , y
                                                    , test_size=0.3
                                                    , random_state=1)

# 학습
knn_model = KNeighborsClassifier(n_neighbors=3)
knn_model.fit(X_train, y_train)

# 예측
knn_model.predict(X_test)

# Score보기
print(knn_model.score(X_test, y_test)) # 98%

pred = knn_model.predict([[ 5.1, 3.5, 1.4, 0.2 ]]) # 0번 데이터 (세토사!)
print('예측 결과: ', target[pred[0]])

# 피클로 저장
with open('data.pickle', 'wb') as f:
    pickle.dump(knn_model, f, pickle.HIGHEST_PROTOCOL)

print('끝!')

# 터미널: ctrl ~ `(백틱) 
# cmd 터미널 : local 설치한 python
# python ./iris.py  --> 실행하라는 의미
# python (엔터)     --> 파이썬 개발을 위한 raw 프로그램 실행 (x)
# dir


