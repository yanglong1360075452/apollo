import axios from 'axios';

export default{
  getAccessLogs(data){
    return axios.get('/netTestLog',{params:data});
  },
  getNetTestLogExport(data){
    return axios.get('/netTestLog/export',{params:data});
  }
}
