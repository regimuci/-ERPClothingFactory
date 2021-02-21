import axios from "axios"
import { Auth } from "aws-amplify";

// Add a request interceptor
let urlProd = "https://cga23r23vi.execute-api.eu-central-1.amazonaws.com/v1";
const instance = axios.create({
  baseURL: urlProd,
})

instance.interceptors.request.use(async function (config) {
  const session = await Auth.currentSession()
  let idToken = session.getIdToken()
  let jwt = idToken.getJwtToken()

  config.headers.Authorization = jwt

  return config;
});

export default instance;