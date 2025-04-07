<template>
  <div class="container">
    <h1 class="title">钣金取图号软件</h1>
    <div class="button-section">
      <button class="custom-button">打开表格</button>
      <button class="custom-button">选择excel</button>
      <input type="text" class="excel-input" />
    </div>
    <div class="form-section">
      <div class="form-item">
        <label class="form-label">类型</label>
        <select class="form-input">
          <option value="">请选择</option>
          <option v-for="type in typeList" :key="type.value" :value="type.value">{{ type.label }}</option>
        </select>
      </div>
      <div class="form-item">
        <label class="form-label">公司</label>
        <input type="text" class="form-input" value="HT" />
      </div>
      <div class="form-item">
        <label class="form-label">流水号</label>
        <input type="text" class="form-input" />
      </div>
      <div class="form-item">
        <label class="form-label">中文名</label>
        <input type="text" class="form-input" />
      </div>
      <div class="radio-section">
        <label>
          <input type="radio" name="position" value="before" checked />
          中文前置
        </label>
        <label class="radio-label">
          <input type="radio" name="position" value="after" />
          中文后置
        </label>
      </div>
    </div>
    <button class="fetch-button">取号</button>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

// 定义类型列表
const typeList = ref([]);
// 定义选中的类型
const selectedType = ref('');
// 定义流水号
const serialNumber = ref('');

// 模拟获取类型列表的接口
const getTypeList = async () => {
  try {
    const response = await axios.get('https://your-api-url.com/types');
    typeList.value = response.data;
  } catch (error) {
    console.error('获取类型列表失败:', error);
  }
};

// 模拟获取流水号的接口
const getSerialNumber = async () => {
  try {
    const response = await axios.get('https://your-api-url.com/serial');
    serialNumber.value = response.data.serial;
  } catch (error) {
    console.error('获取流水号失败:', error);
  }
};

onMounted(async () => {
  await getTypeList();
  await getSerialNumber();
});
</script>

<style scoped>
.container {
  //background-color: skyblue;
  padding: 20px;
}

.title {
  text-align: center;
  color: green;
  //background-color: black;
  padding: 10px;
}

.button-section {
  display: flex;
  margin: 20px 0;
}

.custom-button {
  //background-color: black;
  color: green;
  width: 100px;
  height: 50px;
  margin-right: 20px;
  border: none;
  cursor: pointer;
}

.excel-input {
  height: 30px;
  flex: 1;
}

.form-section {
  display: flex;
  flex-wrap: wrap;
}

.form-item {
  margin-right: 20px;
  margin-bottom: 10px;
}

.form-label {
  display: block;
  text-align: center;
  color: green;
  //background-color: black;
  margin-bottom: 5px;
}

.form-input {
  width: 100px;
  height: 30px;
}

.form-input[name="serial"],
.form-input[name="chineseName"] {
  width: 200px;
}

.radio-section {
  margin-top: 10px;
}

.radio-label {
  margin-left: 10px;
}

.fetch-button {
  //background-color: black;
  color: green;
  width: 300px;
  height: 40px;
  margin-top: 10px;
  border: none;
  cursor: pointer;
}
</style>
