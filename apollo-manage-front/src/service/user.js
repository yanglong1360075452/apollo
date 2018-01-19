import axios from 'axios';

export default{
  getList(data){
    return axios.get('/users',{params:data});
  },
  getexceptionsUsersList(data){
    return axios.get('/users/exceptions',{params:data});
  },
  getNoPermissionUsersList(data){
    return axios.get('/users/nopermission',{params:data});
  },
  getDisabledList(data){
    return axios.get('/users/disabled',{params:data});
  },
  get(data){
    return axios.get('/users/'+data);
  },
  update(data){
    return axios.post("/users",data);
  },
  getFriends(data){
    return axios.get("/users/friends",{params:data});
  },
  getLives(data){
    return axios.get("/lives/liveList",{params:data});
  },
  getSeeLives(data){
    return axios.get("/lives/seeLiveList",{params:data});
  },
  resetPassword(data){
    return axios.post("/users/reset",data);
  },
  getAppRunnings(data){
    return axios.get('/appRunnings',{params:data});
  },
  getNetworks(data){
    return axios.get('/networks',{params:data});
  },
  getAppInstalls(data){
    return axios.get('/appInstalls',{params:data});
  }
}
