/*
*	about us 차트 Chart.js
*/

// All keyword data
const words = ['care', 'skin', 'mask', 'beauty', 'face', 'cream', 'jewelry', 'fashion', 'facial', 'accessories'];
const frequencies = [11236, 5605, 4950, 4158, 3808, 3774, 3321, 3206, 2963, 2578];

// Beauty top 10 keywords data
const beautyKeywords = ['Injection PDRN', 'Lipolysis', 'Makeup Tool', 'Personal Care', 'PDO Thread & Needle', 'Mask', 'Botox', 'Face Skin', 'Wrinkle Care', 'Facial Mask Sheet'];
const beautyKeywordFrequencies = [54, 26, 22, 16, 14, 14, 14, 11, 10, 10];

// Fashion top 10 keywords data
const fashionKeywords = ['Korea Jewelry', 'Earrings', 'Handmade', 'Toddler', 'Costume', 'Baby Shoes', 'Pearl', 'Trendy Fashion', 'Socks', 'Accessories'];
const fashionKeywordFrequencies = [59, 24, 21, 16, 16, 15, 13, 11, 11, 8];

// Food top 10 keywords data
const foodKeywords = ['Emulsifier', 'Pet Food', 'Frozen Dumplings', 'Noodle', 'Halal Rice Cakes', 'Seafood', 'Mandu', 'Ferment', 'Vegan Food', 'Laver Seasoned Flake'];
const foodKeywordFrequencies = [7, 7, 6, 5, 5, 5, 4, 4, 4, 4];

// Other top 10 keywords data
const otherKeywords = ['Polystyrene Moulding', 'Auto Spare Parts', 'Inflatables', 'Home Deco Frame', 'Intermediates', 'Garden Tools', 'Sports Equipment', 'Steering Oil', 'Eyewear', 'Stock Lot Fabric'];
const otherKeywordFrequencies = [107, 91, 88, 86, 67, 46, 39, 34, 30, 29];

// Pie chart (All Top 10 Words)
const ctxPie = document.getElementById('All-pie-chart').getContext('2d');
// Updating Pie chart with larger font size
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
                labels: {
                    font: {
                        size: 16  // Increase legend font size
                    }
                }
            },
            title: {
                display: true,
                text: 'All Top 10 Keyword & Category (Total : 41,994)',
                font: {
                    size: 20  // Increase title font size
                }
            }
        }
    }
});

// Updating horizontal bar charts with larger font sizes
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
            indexAxis: 'y',
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                x: {
                    beginAtZero: true,
                    display: false,
                    grid: {
                        display: false
                    }
                },
                y: {
                    beginAtZero: true,
                    ticks: {
                        font: {
                            size: 14  // Increase y-axis font size
                        },
                        autoSkip: false,
                        maxRotation: 90,
                        minRotation: 0
                    }
                }
            },
            plugins: {
                legend: {
                    position: 'top',
                    labels: {
                        font: {
                            size: 16  // Increase legend font size
                        }
                    }
                },
                title: {
                    display: true,
                    text: `${chartTitle} (Total : ${totalKeywords})`,
                    font: {
                        size: 18  // Increase title font size
                    }
                }
            },
            layout: {
                padding: {
                    left: 10,
                    right: 10,
                    top: 40,
                    bottom: 20
                }
            }
        }
    });
}


// Creating horizontal bar charts for Beauty, Fashion, Food, Other
const ctxKeywordsBar = document.getElementById('Beauty-keywords-bar-chart').getContext('2d');
createHorizontalBarChart(ctxKeywordsBar, beautyKeywords, beautyKeywordFrequencies, 'Beauty Keyword Frequency', 'rgba(54, 162, 235, 0.6)', 'rgba(54, 162, 235, 1)', 'Beauty Top 10 Keyword', 9425);

const ctxFashionKeywordsBar = document.getElementById('fashion-keywords-bar-chart').getContext('2d');
createHorizontalBarChart(ctxFashionKeywordsBar, fashionKeywords, fashionKeywordFrequencies, 'Fashion Keyword Frequency', 'rgba(75, 192, 192, 0.6)', 'rgba(75, 192, 192, 1)', 'Fashion Top 10 Keyword', 1306);

const ctxFoodKeywordsBar = document.getElementById('food-keywords-bar-chart').getContext('2d');
createHorizontalBarChart(ctxFoodKeywordsBar, foodKeywords, foodKeywordFrequencies, 'Food Keyword Frequency', 'rgba(255, 205, 86, 0.6)', 'rgba(255, 205, 86, 1)', 'Food Top 10 Keyword', 973);

const ctxOtherKeywordsBar = document.getElementById('other-keywords-bar-chart').getContext('2d');
createHorizontalBarChart(ctxOtherKeywordsBar, otherKeywords, otherKeywordFrequencies, 'Other Keyword Frequency', 'rgba(153, 102, 255, 0.6)', 'rgba(153, 102, 255, 1)', 'Other Top 10 Keyword', 26033);
