<template>
  <div>
    <div class="top-section">
      <div class="input-group">
        <span>
          类型:
        </span>
        <select v-model="selectedOption" @change="handleSelectChange">
          <option v-for="item in list" :key="item.id" :value="item.id">{{ item.number }}-{{ item.name }}</option>
        </select>
        <span>
          公司:
        </span>
        <input type="text" v-model="input2" />
        <span>
          流水号:
        </span>
        <input type="text" v-model="serialNumber" />
      </div>
      <div class="input-group">
         <span>
          中文:
        </span>
        <input type="text" v-model="chineseName" placeholder="中文名称" />
      </div>
      <div class="input-group">
        <button @click="getNumber">取号键</button>
        <button @click="save">保存键</button>
      </div>
    </div>
    <div class="container mx-auto bg-white p-6 rounded shadow">
      <h1 class="text-2xl font-bold mb-4">数据表格列表</h1>
      <div id="tab" >
        <button
            v-for="(item, index) in list"
            :key="item.id"
            :class="'tab' + (index + 1)"
            @click="handleButtonClick(item.id)"
            :disabled="selectedButtonId === item.id"
        >
          {{ item.number }}-{{ item.name }}
        </button>
      </div>
      <table id="dataTable" class="min-w-full divide-y divide-gray-200">
        <thead class="bg-gray-50">
        <tr>
          <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
            编号
          </th>
          <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
            图号
          </th>
          <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
            名称
          </th>
        </tr>
        </thead>
        <tbody id="tableBody" class="bg-white divide-y divide-gray-200">
        <tr v-for="(item,index) in slicedData" :key="item.id">
          <td class="px-6 py-4 whitespace-nowrap">{{ index+1 }}</td>
          <td class="px-6 py-4 whitespace-nowrap">{{ item.serialNumber }}{{ item.serialNumber }}</td>
          <td class="px-6 py-4 whitespace-nowrap">{{ item.name }}</td>
        </tr>
        </tbody>
      </table>
      <div class="flex justify-between mb-4">
        <div>
          <label for="pageSize" class="mr-2">每页显示数量:</label>
          <select v-model="pageSize" @change="renderData">
            <option value="10">10 条</option>
            <option value="20">20 条</option>
            <option value="30">30 条</option>
          </select>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      list: [],
      selectedOption: null,
      input2: 'HT',
      serialNumber: '',
      chineseName: '',
      pageSize: 10,
      selectedButtonId: null,
      data: []
    };
  },
  computed: {
    slicedData() {
      return this.data.slice(0, this.pageSize);
    }
  },
  mounted() {
    this.fetchData();
    this.renderData();
  },
  methods: {
    fetchData() {
      // 替换为实际的接口地址
      axios.get('http://localhost:8080/metalPlate/list')
          .then(response => {
            this.list = response.data.page.list;
            // 当数据获取成功后，将 selectedOption 设置为第一个选项的值
            if (this.list.length > 0) {
              this.selectedOption = this.list[0].id;
            }
          })
          .catch(error => {
            console.error('获取数据失败:', error);
          });
    },getNumberList(id) {
      const params = {
        typeId : id,
        pageSize:this.pageSize
      }
      // 替换为实际的接口地址
      axios.post('http://localhost:8080/metalPlateNumber/list',params)
          .then(response => {
            this.data = null;
            this.data = response.data.page.list;
          })
          .catch(error => {
            console.error('获取数据失败:', error);
          });
    },
    getNumber() {
      const params = {
        typeId : this.selectedOption
      }
      axios.post('http://localhost:8080/metalPlateNumber/max',params)
          .then(response => {
            const data = response.data
            if ('success' === data.msg) {
              this.serialNumber = data.data
            }
          })
          .catch(error => {
            console.error('取号失败:', error);
          });
    },
    save(){
      // 构建要传递的 JSON 参数
      const numberJson = {
        corp: this.input2,
        serialNumber: this.serialNumber,
        name: this.chineseName,
        typeId: this.selectedOption,
        rules: this.rules
      };
      // 替换为实际的取号接口地址
      axios.post('http://localhost:8080/metalPlateNumber/save', numberJson)
          .then(response => {
            if ('success' === response.data.msg) {
              this.fetchData();
            }
          })
          .catch(error => {
            console.error('取号失败:', error);
          });
    },
    handleButtonClick(id) {
      this.selectedButtonId = id; // 更新当前被点击的按钮的 id
      this.getNumberList(id);
    },
    handleSelectChange() {
      this.selectedButtonId = null; // 当选择框改变时，重置被点击的按钮状态
    },
    renderData() {
      // 数据渲染逻辑已在 computed 属性中处理
    }
  }
};
</script>

<style scoped>
body {
  font-family: Arial, sans-serif;
  margin: 0;
  padding: 20px;
}

span {
  text-align: center;
  font-weight: bold;
}

.top-section {
  text-align: center;
  padding: 20px;
  border: 2px solid black;
}

.input-group {
  margin-bottom: 20px;
}
#tab button {
  margin-top: 20px;
  margin-right: 100px; /* 添加按钮右边距，可根据需要调整数值 */
}
input {
  padding: 5px;
  margin-right: 10px;
}

select {
  padding: 5px;
  margin-right: 10px;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

th,
td {
  border: 1px solid #ccc;
  padding: 8px;
  text-align: left;
}

th {
  background-color: #f2f2f2;
}

#tableSelect {
  text-align: center;
  padding: 20px;
  border: 2px solid black;
}
</style>
