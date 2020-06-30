<template>
<div>
  <a-upload
    name="avatar"
    list-type="picture-card"
    class="avatar-uploader"
    :show-upload-list="false"
    :before-upload="beforeUpload"
    @change="handleChange"
  >
    <img v-if="imageUrl" :src="imageUrl" alt="avatar" style="width: 100%" />
    <div v-else>
      <a-icon :type="loading ? 'loading' : 'plus'" />
      <div class="ant-upload-text">
        Upload
      </div>
    </div>
  </a-upload>
</div>
</template>
<script>
import client from '@/utils/oss'
import moment from 'moment'
import {mapGetters,mapActions} from 'vuex'
function getBase64(img, callback) {
  const reader = new FileReader();
  reader.addEventListener('load', () => callback(reader.result));
  reader.readAsDataURL(img);
}
// const client = new oss({
//     // accessKeyId: '',
//     // accessKeySecret: '',
//     // 公开到github之前把下面的删掉！
//     accessKeyId: 'LTAI4G1YFPYT4T4Jjejgw4F7',
//     accessKeySecret: 'vAX8e8pc9jJuzlqPUXFdiEPq4JYF4e',
//     region: 'oss-cn-beijing', //
//     bucket: 'nju-hotel',//
//     host:'nju-hotel.oss-cn-beijing.aliyuncs.com'
// })
export default {
  data() {
    return {
      loading: false,
      imageUrl: '',
    };
  },
  computed:{
    ...mapGetters(['avatar'])
  },
  mounted(){
    this.imageUrl = this.avatar
  },
  methods: {
    ...mapActions(['updateAvatar']),
    uploadPath(path, file){
        // 上传文件的路径，使用日期命名文件目录
        return `${moment().format('YYYYMMDD')}/${file.name.split(".")[0]}-${file.uid}.${file.type.split("/")[1]}`
    },
    UploadToOss(self, path, file){
      console.log(client); 
      const url = this.uploadPath(path, file)
      return new Promise((resolve, reject) => {
        // 上传到阿里云oss，返回的data.res.requestUrls是上传后的url
        // 然后调用后端方法，更新数据库中的url
          client.multipartUpload(url, file).then(data => {
          resolve(data);
            var targetUrl = data.res.requestUrls[0]
            this.imageUrl = targetUrl;
            this.updateAvatar(targetUrl)
          }).catch(error => {
            reject(error)
          })
      })
    },
    handleChange(info) {
      if (info.file.status === 'uploading') {
        this.loading = true;
        return;
      }
      if (info.file.status === 'done') {
        // Get this url from response in real world.
        getBase64(info.file.originFileObj, imageUrl => {
          this.imageUrl = imageUrl;
          this.loading = false;
        });
      }
    },
    beforeUpload(file) {
      const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
      if (!isJpgOrPng) {
        this.$message.error('You can only upload JPG file!');
      }
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isLt2M) {
        this.$message.error('Image must smaller than 2MB!');
      }
    this.UploadToOss(this, '上传路径oss配置信息', file)
    //   return isJpgOrPng&&isLt2M; // 不使用默认上传方式
      return false; // 不使用默认上传方式
    },
  },
};
</script>
<style scoped>
.avatar-uploader > .ant-upload {
  width: 128px;
  height: 128px;
}
.ant-upload-select-picture-card i {
  font-size: 32px;
  color: #999;
}

.ant-upload-select-picture-card .ant-upload-text {
  margin-top: 8px;
  color: #666;
}
</style>