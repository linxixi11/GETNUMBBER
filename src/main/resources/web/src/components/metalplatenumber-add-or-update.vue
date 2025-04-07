<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="" prop="corp">
      <el-input v-model="dataForm.corp" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="serialNumber">
      <el-input v-model="dataForm.serialNumber" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="name">
      <el-input v-model="dataForm.name" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="roules">
      <el-input v-model="dataForm.roules" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="created">
      <el-input v-model="dataForm.created" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="lastUpdated">
      <el-input v-model="dataForm.lastUpdated" placeholder=""></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          corp: '',
          serialNumber: '',
          name: '',
          roules: '',
          created: '',
          lastUpdated: ''
        },
        dataRule: {
          corp: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          serialNumber: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          name: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          roules: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          created: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          lastUpdated: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`//metalplatenumber/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.corp = data.metalPlateNumber.corp
                this.dataForm.serialNumber = data.metalPlateNumber.serialNumber
                this.dataForm.name = data.metalPlateNumber.name
                this.dataForm.roules = data.metalPlateNumber.roules
                this.dataForm.created = data.metalPlateNumber.created
                this.dataForm.lastUpdated = data.metalPlateNumber.lastUpdated
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`//metalplatenumber/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'corp': this.dataForm.corp,
                'serialNumber': this.dataForm.serialNumber,
                'name': this.dataForm.name,
                'roules': this.dataForm.roules,
                'created': this.dataForm.created,
                'lastUpdated': this.dataForm.lastUpdated
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
