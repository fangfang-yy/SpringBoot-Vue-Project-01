/**
 * Created by PanJiaChen on 16/11/18.
 */

/**
 * @param {string} path
 * @returns {Boolean}
 */
export function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validUsername(str) {
  // 校验str是否是valid_map数组中的一种
  // const valid_map = ['admin', 'editor']
  // return valid_map.indexOf(str.trim()) >= 0

  // 校验str去除空格后长度是否大于零
  return str.trim().length > 0
}
