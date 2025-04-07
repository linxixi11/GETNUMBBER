<template>
  <div class="container">
    <!-- 上方操作区域 -->
    <div class="header-box">
      <span class="fixed-part">8</span>
      <input v-model="htPrefix" placeholder="HT" class="input-box" />
      <span class="serial-part">0001</span>
      <input v-model="chineseName" placeholder="中文名称" class="input-box" />
      <button @click="generateDrawingNo" class="fetch-btn">取号键</button>
    </div>

    <!-- 标题部分 -->
    <h2 class="title">航都板环保柜钣金清单</h2>

    <!-- 表格部分 -->
    <el-table :data="tableData" style="width: 100%" class="table-container">
      <el-table-column type="index" label="序号" width="50"></el-table-column>
      <el-table-column prop="drawingNo" label="图号"></el-table-column>
      <el-table-column prop="name" label="名称"></el-table-column>
      <el-table-column prop="material" label="材质"></el-table-column>
      <el-table-column prop="thickness" label="厚度"></el-table-column>
      <el-table-column prop="quantity" label="数量"></el-table-column>
      <el-table-column prop="boundaryLength" label="边界框长度"></el-table-column>
      <el-table-column prop="boundaryWidth" label="边界框宽度"></el-table-column>
      <el-table-column prop="section" label="截图"></el-table-column>
      <el-table-column prop="remark" label="备注"></el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      htPrefix: "HT", // HT 前缀
      chineseName: "", // 中文名称
      tableData: [
        {
          drawingNo: "8HT0001",
          name: "",
          material: "",
          thickness: "",
          quantity: "",
          boundaryLength: "",
          boundaryWidth: "",
          section: "",
          remark: ""
        }
      ]
    };
  },
  methods: {
    generateDrawingNo() {
      // 简单模拟流水号递增（实际需更严谨处理）
      const lastItem = this.tableData[this.tableData.length - 1];
      const serial = parseInt(lastItem.drawingNo.slice(-4)) + 1;
      const newDrawingNo = `8${this.htPrefix}${serial.toString().padStart(4, "0")}`;

      this.tableData.push({
        drawingNo: newDrawingNo,
        name: "",
        material: "",
        thickness: "",
        quantity: "",
        boundaryLength: "",
        boundaryWidth: "",
        section: "",
        remark: ""
      });
    }
  }
};
</script>

<style scoped>
.container {
  padding: 20px;
}

.header-box {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.fixed-part {
  margin-right: 10px;
  font-size: 18px;
}

.input-box {
  margin-right: 10px;
  padding: 5px;
}

.fetch-btn {
  padding: 5px 15px;
}

.title {
  text-align: center;
  margin-bottom: 15px;
}

.table-container {
  margin-top: 15px;
}
</style>
