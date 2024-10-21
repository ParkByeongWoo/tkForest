// All keyword data
const words = ['care', 'skin', 'mask', 'beauty', 'face', 'cream', 'jewelry', 'fashion', 'facial', 'accessories'];
const frequencies = [11236, 5605, 4950, 4158, 3808, 3774, 3321, 3206, 2963, 2578];

// Beauty top 10 keywords data
const beautyKeywords = ['INJECTION PDRN', 'LIPOLYSIS', 'MAKEUP TOOL', 'PERSONAL CARE', 'PDO THREAD & NEEDLE', 'MASK', 'BOTOX', 'FACE SKIN', 'WRINKLE CARE', 'FACIAL MASK SHEET'];
const beautyKeywordFrequencies = [54, 26, 22, 16, 14, 14, 14, 11, 10, 10];

// Fashion top 10 keywords data
const fashionKeywords = ['KOREA JEWELRY', 'EARRINGS', 'HANDMADE', 'TODDLER', 'COSTUME', 'BABY SHOES', 'PEARL', 'TRENDY FASHION', 'SOCKS', 'ACCESSORIES'];
const fashionKeywordFrequencies = [59, 24, 21, 16, 16, 15, 13, 11, 11, 8];

// Food top 10 keywords data
const foodKeywords = ['EMULSIFIER', 'PET FOOD', 'FROZEN DUMPLINGS', 'NOODLE', 'HALAL RICE CAKES', 'SEAFOOD', 'MANDU', 'FERMENT', 'VEGAN FOOD', 'LAVER SEASONED FLAKE'];
const foodKeywordFrequencies = [7, 7, 6, 5, 5, 5, 4, 4, 4, 4];

// Other top 10 keywords data
const otherKeywords = ['POLYSTYRENE MOULDING', 'AUTO SPARE PARTS', 'INFLATABLES', 'HOME DECO FRAME', 'INTERMEDIATES', 'Garden Tools', 'sports equipment', 'STEERING OIL', 'EYEWEAR', 'STOCK LOT FABRIC'];
const otherKeywordFrequencies = [107, 91, 88, 86, 67, 46, 39, 34, 30, 29];

// Pie chart (All Top 10 Words)
const ctxPie = document.getElementById('All-pie-chart').getContext('2d');
const pieChart = new Chart(ctxPie, {
    type: 'pie',
    data: {
        labels: words,
        datasets: [{
            label: 'Keyword Frequency',
            data: frequencies,
            backgroundColor: [
                'rgba(255, 99, 132, 0.6)',
                'rgba(54, 162, 235, 0.6)',
                'rgba(255, 206, 86, 0.6)',
                'rgba(75, 192, 192, 0.6)',
                'rgba(153, 102, 255, 0.6)',
                'rgba(255, 159, 64, 0.6)',
                'rgba(100, 255, 100, 0.6)',
                'rgba(255, 100, 255, 0.6)',
                'rgba(100, 100, 255, 0.6)',
                'rgba(255, 255, 100, 0.6)'
            ],
            borderColor: [
                'rgba(255, 99, 132, 1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)',
                'rgba(153, 102, 255, 1)',
                'rgba(255, 159, 64, 1)',
                'rgba(100, 255, 100, 1)',
                'rgba(255, 100, 255, 1)',
                'rgba(100, 100, 255, 1)',
                'rgba(255, 255, 100, 1)'
            ],
            borderWidth: 1
        }]
    },
    options: {
        responsive: true,
        plugins: {
            legend: {
                position: 'top',
            },
            title: {
                display: true,
                text: 'All Top 10 Keyword & Category  (Total : 41,994)' // 총 키워드 수 직접 입력
            }
        }
    }
});

// Horizontal Bar Chart creation function
function createHorizontalBarChart(ctx, labels, data, label, bgColor, borderColor, chartTitle, totalKeywords) {
    return new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: label,
                data: data,
                backgroundColor: bgColor,
                borderColor: borderColor,
                borderWidth: 1
            }]
        },
        options: {
            indexAxis: 'y',  // Horizontal bar chart
            responsive: true,
            maintainAspectRatio: false,  // 부모 요소 크기에 맞춰 비율 유지
            scales: {
                x: {
                    beginAtZero: true,
                    display: false,  // x축 숨김
                    grid: {
                        display: false  // x축의 그리드 라인도 숨김
                    }
                },
                y: {
                    beginAtZero: true,
                    ticks: {
                        autoSkip: false,  // y축 레이블 자동 생략 비활성화
                        maxRotation: 90,  // y축 레이블 회전 (필요시 조정)
                        minRotation: 0    // 최소 회전 각도 설정
                    }
                }
            },
            plugins: {
                legend: {
                    position: 'top',
                },
                title: {
                    display: true,
                    text: `${chartTitle} (Total : ${totalKeywords})` // 총 키워드 수 직접 입력
                },
                tooltip: {
                    enabled: true  // 툴팁 활성화
                }
            },
            layout: {
                padding: {
                    left: 10,
                    right: 10,
                    top: 40,  // 텍스트 공간 확보
                    bottom: 20
                }
            }
        }
    });
}

// Creating horizontal bar charts for Beauty, Fashion, Food, Other
const ctxKeywordsBar = document.getElementById('Beauty-keywords-bar-chart').getContext('2d');
createHorizontalBarChart(ctxKeywordsBar, beautyKeywords, beautyKeywordFrequencies, 'Beauty Keyword Frequency', 'rgba(54, 162, 235, 0.6)', 'rgba(54, 162, 235, 1)', 'Beauty Top 10 Keyword', 211);

const ctxFashionKeywordsBar = document.getElementById('fashion-keywords-bar-chart').getContext('2d');
createHorizontalBarChart(ctxFashionKeywordsBar, fashionKeywords, fashionKeywordFrequencies, 'Fashion Keyword Frequency', 'rgba(75, 192, 192, 0.6)', 'rgba(75, 192, 192, 1)', 'Fashion Top 10 Keyword', 188);

const ctxFoodKeywordsBar = document.getElementById('food-keywords-bar-chart').getContext('2d');
createHorizontalBarChart(ctxFoodKeywordsBar, foodKeywords, foodKeywordFrequencies, 'Food Keyword Frequency', 'rgba(255, 205, 86, 0.6)', 'rgba(255, 205, 86, 1)', 'Food Top 10 Keyword', 61);

const ctxOtherKeywordsBar = document.getElementById('other-keywords-bar-chart').getContext('2d');
createHorizontalBarChart(ctxOtherKeywordsBar, otherKeywords, otherKeywordFrequencies, 'Other Keyword Frequency', 'rgba(153, 102, 255, 0.6)', 'rgba(153, 102, 255, 1)', 'Other Top 10 Keyword', 617);