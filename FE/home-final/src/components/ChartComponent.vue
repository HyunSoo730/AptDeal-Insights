<template>
    <div>
      <canvas ref="canvas"></canvas>
    </div>
  </template>
  
  <script lang="ts" setup>
  import { onMounted, ref, watch } from 'vue';
  import { Chart, ChartData, ChartOptions } from 'chart.js';
  
  const props = defineProps<{
    chartData: ChartData;
    options: ChartOptions;
  }>();
  
  const canvas = ref<HTMLCanvasElement | null>(null);
  let chartInstance: Chart | null = null;
  
  const renderChart = () => {
    if (canvas.value && props.chartData) {
      if (chartInstance) {
        chartInstance.destroy();
      }
      chartInstance = new Chart(canvas.value, {
        type: 'line',
        data: props.chartData,
        options: props.options,
      });
    }
  };
  
  onMounted(renderChart);
  
  watch(() => props.chartData, renderChart);
  </script>
  