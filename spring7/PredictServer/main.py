# package import
import pandas as pd
import numpy as np
import pickle

# 설치 방법
# pip install FastAPI
# pip install fastapi uvicorn[standard]
# pip list install

from fastapi import FastAPI
import uvicorn
from pydantic import BaseModel
from fastapi.responses import JSONResponse

# Model 생성
class Item(BaseModel):
    petalLength: float
    petalWidth: float
    sepalLength: float
    sepalWidth: float

app = FastAPI()

@app.post(path="/items", status_code=201)
def myiris(item : Item) :
    # 학습 모델(피클파일) 로딩
    with open('./data.pickle', 'rb') as f:
        knn_model = pickle.load(f)
        dicted = dict(item)

        petalLength = dicted['petalLength']
        petalWidth = dicted['petalWidth']
        sepalLength = dicted['sepalLength']
        sepalWidth = dicted['sepalWidth']

        print(petalLength);

        # 분석하기 위한 2차원 데이터로 변경
        X = np.array([[sepalLength, sepalWidth, petalLength, petalWidth ]])
        target = ['setosa', 'vesicolor', 'virginica']

        pred = knn_model.predict(X)
        result = { "predict_result" : target[pred[0]] }

        print("=========== 예측 반환값 =========== ", pred)
        print("=========== JSON 결과값 =========== ", result)

    return JSONResponse(result)

if __name__ == '__main__' :
    uvicorn.run(app, host='127.0.0.1', port=8000)
