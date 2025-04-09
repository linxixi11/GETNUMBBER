<template>
  <div class="min-h-screen bg-gradient-to-br from-gray-100 to-gray-200 p-8">
    <!-- 头部操作卡片 -->
    <div class="bg-gradient-to-r from-blue-500 to-purple-600 p-8 rounded-2xl shadow-2xl mb-8 transform transition-all hover:shadow-3xl">
      <div class="grid grid-cols-1 md:grid-cols-2 gap-6 mb-6">
        <!-- 左侧输入组 -->
        <div class="space-y-4">
          <div class="flex items-center gap-3">
            <label class="w-24 text-white/80 font-medium">类型:</label>
            <select
                v-model="selectedOption"
                @change="handleSelectChange"
                class="flex-1 p-3 bg-white/20 backdrop-blur-sm rounded-xl border border-white/30 focus:ring-2 focus:ring-white/50 text-white placeholder-white/50 transition-all"
            >
              <option
                  v-for="item in list"
                  :key="item.id"
                  :value="item.id"
                  class="text-gray-800"
              >
                {{ item.number }}-{{ item.name }}
              </option>
            </select>
          </div>
          <div class="flex items-center gap-3">
            <label class="w-24 text-white/80 font-medium">公司:</label>
            <input
                type="text"
                v-model="input2"
                class="flex-1 p-3 bg-white/20 backdrop-blur-sm rounded-xl border border-white/30 focus:ring-2 focus:ring-white/50 text-white placeholder-white/50 transition-all"
            />
          </div>
        </div>

        <!-- 右侧输入组 -->
        <div class="space-y-4">
          <div class="flex items-center gap-3">
            <label class="w-24 text-white/80 font-medium">流水号:</label>
            <input
                type="text"
                v-model="serialNumber"
                class="flex-1 p-3 bg-white/20 backdrop-blur-sm rounded-xl border border-white/30 focus:ring-2 focus:ring-white/50 text-white placeholder-white/50 transition-all"
            />
          </div>
          <div class="flex items-center gap-3">
            <label class="w-24 text-white/80 font-medium">中文:</label>
            <input
                type="text"
                v-model="chineseName"
                placeholder="中文名称"
                class="flex-1 p-3 bg-white/20 backdrop-blur-sm rounded-xl border border-white/30 focus:ring-2 focus:ring-white/50 text-white placeholder-white/50 transition-all"
            />
          </div>
        </div>
      </div>

      <!-- 操作按钮 -->
      <div class="flex gap-4">
        <button
            @click="getNumber"
            class="px-6 py-3 bg-emerald-400 hover:bg-emerald-500 text-white font-semibold rounded-xl shadow-md hover:shadow-lg transition-all transform hover:-translate-y-0.5"
        >
          <i class="fas fa-magic mr-2"></i>取号
        </button>
        <button
            @click="save"
            class="px-6 py-3 bg-amber-400 hover:bg-amber-500 text-white font-semibold rounded-xl shadow-md hover:shadow-lg transition-all transform hover:-translate-y-0.5"
        >
          <i class="fas fa-save mr-2"></i>保存
        </button>
      </div>
    </div>

    <!-- 数据展示卡片 -->
    <div class="bg-white/90 backdrop-blur-lg rounded-2xl shadow-2xl p-8">
      <!-- 标签切换 -->
      <div class="flex flex-wrap gap-3 mb-6">
        <button
            v-for="(item) in list"
            :key="item.id"
            @click="handleButtonClick(item.id)"
            :class="[
              'px-5 py-2.5 rounded-xl font-medium transition-all',
              'shadow-md hover:shadow-lg',
              selectedButtonId === item.id
                ? 'bg-gradient-to-r from-blue-400 to-purple-500 text-white'
                : 'bg-gray-100 hover:bg-gray-200 text-gray-700'
            ]"
        >
          <i class="fas fa-tag mr-2"></i>
          {{ item.number }}-{{ item.name }}
        </button>
      </div>

      <!-- 数据表格 -->
      <div class="overflow-x-auto rounded-xl shadow-lg border border-gray-100">
        <table class="w-full">
          <thead class="bg-gradient-to-r from-blue-400 to-purple-500 text-white">
          <tr>
            <th class="px-6 py-4 text-left font-semibold text-sm uppercase tracking-wider">编号</th>
            <th class="px-6 py-4 text-left font-semibold text-sm uppercase tracking-wider">图号</th>
            <th class="px-6 py-4 text-left font-semibold text-sm uppercase tracking-wider">名称</th>
          </tr>
          </thead>
          <tbody class="divide-y divide-gray-200/50 bg-white">
          <tr
              v-for="(item, index) in paginatedData"
              :key="item.id"
              class="hover:bg-gray-50/80 transition-colors"
          >
            <td class="px-6 py-4 font-mono text-blue-600 font-medium">
              #{{ (currentPage - 1) * pageSize + index + 1 }}
            </td>
            <td class="px-6 py-4 font-medium text-gray-700">
              <span class="bg-gray-100 px-3 py-1 rounded-md">{{ item.serialNumber }}</span>
            </td>
            <td class="px-6 py-4 text-gray-600">{{ item.name }}</td>
          </tr>
          </tbody>
        </table>
      </div>

      <!-- 分页控制 -->
      <div class="mt-8 flex flex-col md:flex-row justify-between items-center gap-4">
        <div class="flex items-center gap-3">
          <span class="text-gray-600">每页显示:</span>
          <select
              v-model="pageSize"
              @change="currentPage = 1"
              class="p-2 bg-white border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-400 shadow-sm"
          >
            <option value="10">10</option>
            <option value="20">20</option>
            <option value="30">30</option>
          </select>
          <span class="text-gray-600">共 {{ totalItems }} 条记录</span>
        </div>

        <div class="flex items-center gap-2">
          <button
              @click="currentPage--"
              :disabled="currentPage === 1"
              :class="[
                'px-4 py-2 rounded-lg font-medium transition-all',
                currentPage === 1
                  ? 'bg-gray-100 text-gray-400 cursor-not-allowed'
                  : 'bg-blue-100 text-blue-600 hover:bg-blue-200 shadow-md hover:shadow-lg'
              ]"
          >
            <i class="fas fa-chevron-left mr-2"></i>上一页
          </button>
          <span class="px-4 py-2 text-gray-600 font-medium">
            第 {{ currentPage }} 页 / 共 {{ totalPages }} 页
          </span>
          <button
              @click="currentPage++"
              :disabled="currentPage === totalPages"
              :class="[
                'px-4 py-2 rounded-lg font-medium transition-all',
                currentPage === totalPages
                  ? 'bg-gray-100 text-gray-400 cursor-not-allowed'
                  : 'bg-blue-100 text-blue-600 hover:bg-blue-200 shadow-md hover:shadow-lg'
              ]"
          >
            下一页<i class="fas fa-chevron-right ml-2"></i>
          </button>
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
      selectedButtonId: null,
      data: [],
      currentPage: 1,
      pageSize: 10,
    };
  },
  computed: {
    totalItems() {
      return this.data.length;
    },
    totalPages() {
      return Math.ceil(this.totalItems / this.pageSize);
    },
    paginatedData() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.data.slice(start, end);
    }
  },
  watch: {
    pageSize() {
      this.currentPage = 1;
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

<style>
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css');

/* 添加自定义滚动条样式 */
::-webkit-scrollbar {
  width: 8px;
  height: 8px;
}

::-webkit-scrollbar-track {
  @apply bg-gray-100 rounded-full;
}

::-webkit-scrollbar-thumb {
  @apply bg-gradient-to-b from-blue-400 to-purple-500 rounded-full;
}

/* 表格行入场动画 */
tr {
  animation: fadeIn 0.3s ease-in;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>