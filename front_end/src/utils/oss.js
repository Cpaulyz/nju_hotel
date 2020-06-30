import oss from 'ali-oss'

const client = new oss({
	// 此处填写阿里云oss信息
    accessKeyId: '',
    accessKeySecret: '',
    region: 'oss-cn-beijing', //
    bucket: 'nju-hotel',//
    host:'nju-hotel.oss-cn-beijing.aliyuncs.com'
})

export default client