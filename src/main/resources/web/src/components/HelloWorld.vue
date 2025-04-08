<template>
  <div>
    <select v-model="selectedOption">
      <option value="">请选择</option>
      <option v-for="option in options" :key="option.id" :value="option.id">{{ option.name }}</option>
    </select>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

// 定义选项列表
const options = ref([])

// 获取接口数据
const fetchOptions = async () => {
  try {
    // 替换为实际的接口地址
    const response = await axios.get('/metalPlate/list')
    options.value = response.data.page.list
  } catch (error) {
    console.error('获取选项数据失败:', error)
  }
}

onMounted(async () => {
  await fetchOptions()
})
</script>

<style scoped>
/* 可根据需要添加样式 */
</style>
