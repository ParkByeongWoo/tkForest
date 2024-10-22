# !pip install nest_asyncio

import os


import pandas as pd
import numpy as np
import pickle
from fastapi import FastAPI
from pydantic import BaseModel
from fastapi.responses import JSONResponse
import uvicorn
import nest_asyncio

# FastAPI 인스턴스 생성
app = FastAPI()

# 엑셀 파일 로드
df_concat = pd.read_excel("./RecServer/df_cancat.xlsx")
df_item = pd.read_excel("./RecServer/상품.xlsx")

# Pydantic 모델 정의
class Item(BaseModel):
    buyerMemberNo: str

@app.post(path="/items", status_code=201)
def myrec(item: Item):
    with open('./RecServer/1sorted_idx', 'rb') as f:
        sorted_idx = pickle.load(f)

    dicted = dict(item)
    num = dicted['buyerMemberNo']
    print(num)
    
    # 상위 50개 제품 추출
    top_50_productno = df_concat.iloc[sorted_idx[df_concat[df_concat['TARGETNO'] == num].index[0]-38965, :50]]['TARGETNO'].unique()

    # 파일 실행 방식의 경우 이 코드 사용
    # top_50_productno = df_concat.iloc[sorted_idx[df_concat[df_concat['TARGETNO'] == num].index[0], :50]]['TARGETNO'].unique()

    # df_item이 정의되어 있어야 합니다
    df_top_50 = df_item[df_item['PRODUCTNO'].isin(top_50_productno)]
    df_top_50['sort_order'] = pd.Categorical(df_top_50['PRODUCTNO'], categories=top_50_productno, ordered=True)
    df_top_50 = df_top_50.sort_values('sort_order').drop('sort_order', axis=1)

    # 정렬 기준을 유지
    df_top_50 = df_top_50.reset_index(drop=True)

    # 중복된 'PRODUCTNO'에서 'Total_Score'가 가장 큰 행만 남기기
    unique_items = df_top_50.sort_values('Total_Score', ascending=False).drop_duplicates(subset='PRODUCTNAME', keep='first').sort_index()

    final_recommendations = []  # 최종 추천 상품 10개를 담을 리스트
    companies_seen = set()  # 리스트에 포함된 상품의 회사를 기록

    # 상위 상품 5개 추천
    while len(final_recommendations) < 5 and not unique_items.empty:
        sample = unique_items.head(1)
        company = sample['BUSINESSNO'].values[0]

        if company not in companies_seen:
            final_recommendations.append(sample)
            companies_seen.add(company)

        unique_items = unique_items.drop(sample.index)

    # 랜덤 5개 추천
    while len(final_recommendations) < 10 and not unique_items.empty:
        sample = unique_items.sample(1)
        company = sample['BUSINESSNO'].values[0]

        if company not in companies_seen:
            final_recommendations.append(sample)
            companies_seen.add(company)

        unique_items = unique_items.drop(sample.index)

    # 각 추천 항목을 문자열로 변환
    final_recommendations_str = [sample.to_string(index=False) for sample in final_recommendations]

    print(final_recommendations_str)
    
    return JSONResponse(final_recommendations_str)

# Uvicorn 서버 실행
nest_asyncio.apply()

if __name__ == '__main__':
    uvicorn.run("main:app", host="127.0.0.1", port=8000, log_level="info")


