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

df_item = pd.read_excel("C:/RecServer/상품.xlsx")
df_buyer = pd.read_excel("C:/RecServer/바이어(행동데이터o).xlsx")
# Pydantic 모델 정의
class Item(BaseModel):
    buyerMemberNo: str

@app.post(path="/items", status_code=201)
def myrec(item: Item) :
    dicted = dict(item)
    num = dicted['buyerMemberNo']
    if num.startswith('B') :
        with open('C:/RecServer/1sorted_idx.pkl', 'rb') as f :
            # 엑셀 파일 로드
            df_concat = pd.read_excel("C:/RecServer/df_cancat.xlsx")
            sorted_idx = pickle.load(f)
            num = int(num.lstrip('B'))
            print(num)
            print(df_concat)
            print(df_concat[df_concat['TARGETNO']==389195])
            # 상위 50개 제품 추출
            top_50_productno = df_concat.iloc[sorted_idx[df_concat[df_concat['TARGETNO'] == num].index[0]-38965, :60]]['TARGETNO'].unique()

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

            recProducts = []
            for i in final_recommendations:
                recProducts.append(int(i['PRODUCTNO']))
            print(recProducts)

        return JSONResponse(content={"recommendations": recProducts})
    else :
        with open('C:/RecServer/combined_matrix.pkl', 'rb') as f :
            print(num)
            num = int(num)
            combined_matrix = pickle.load(f)
            sorted_idx = combined_matrix.values.argsort(axis=1)[:,::-1]

            index_position = combined_matrix.index.get_loc(num)

            df_top_50 = df_item[df_item['PRODUCTNO'].isin([combined_matrix.columns[i] for i in list(sorted_idx[index_position,:50])])]

            # 처음 데이터프레임의 정렬 기준을 유지
            df_top_50 = df_top_50.reset_index(drop=True)

            # 중복된 'PRODUCTNO'에서 'Total_Score'가 가장 큰 행만 남기기
            unique_items = df_top_50.sort_values('Total_Score', ascending=False).drop_duplicates(subset='PRODUCTNAME', keep='first').sort_index()

            # 10개의 상품을 랜덤으로 선택하되, 동일한 회사의 상품이 중복되지 않도록 처리
            final_recommendations = []    # 최종 추천 상품 10개를 담을 리스트
            companies_seen = set()    # 리스트에 포함된 상품의 회사를 기록

            # 상위 상품 5개 추천
            while len(final_recommendations) < 5 and not unique_items.empty:
                sample = unique_items.head(1)  # 50개의 상품 중 상위 상품 중 하나
                company = sample['BUSINESSNO'].values[0]   # 상품의 회사고유번호

                if not sample['PRODUCTNO'].isin(df_buyer[df_buyer['MEMBERNO'] == num]['INQUIRY_PRODUCT']).any():
                    if company not in companies_seen:    # 리스트에 중복되는 회사가 없으면 코드 실행
                        final_recommendations.append(sample)    # 최종 추천 10개에 해당 상품 추가
                        companies_seen.add(company)    # 해당 회사의 상품이 추천 리스트에 포함되었음을 기록

                unique_items = unique_items.drop(sample.index)
                # uniuqe_items 데이터프레임에서 'sample'의 인덱스를 가진 행을 삭제
                # 선택된 샘픔을 'unique_items'에서 제거하여 중복 선택을 방지

            # 랜덤 5개 추천
            while len(final_recommendations) < 10 and not unique_items.empty:
                sample = unique_items.sample(1)  # 50개의 상품 중 랜덤하게 선택한 샘플 하나
                company = sample['BUSINESSNO'].values[0]   # 샘플의 회사고유번호

                if not sample['PRODUCTNO'].isin(df_buyer[df_buyer['MEMBERNO'] == num]['INQUIRY_PRODUCT']).any():
                    if company not in companies_seen:    # 리스트에 중복되는 회사가 없으면 코드 실행
                        final_recommendations.append(sample)    # 최종 추천 10개에 해당 샘플 추가
                        companies_seen.add(company)    # 해당 회사의 상품이 추천 리스트에 포함되었음을 기록

                unique_items = unique_items.drop(sample.index)
                # uniuqe_items 데이터프레임에서 'sample'의 인덱스를 가진 행을 삭제
                # 선택된 샘픔을 'unique_items'에서 제거하여 중복 선택을 방지
            recProducts = []
            for i in final_recommendations:
                recProducts.append(int(i['PRODUCTNO']))
            print(recProducts)

        return JSONResponse(content={"recommendations": recProducts})

# Uvicorn 서버 실행
# nest_asyncio.apply()

if __name__ == '__main__':
    uvicorn.run("main:app", host="127.0.0.1", port=8000, log_level="info")
    
# uvicorn main:app --reload