class _const(object):
    class ConstError(TypeError):pass
    def __setattr__(self, key, value):#方法覆盖
        if self.__dict__.keys().__contains__(key):
            raise self.ConstError("Can't rebind const")
        self.__dict__[key] = value
    def __delitem__(self, key):
        if key in self.__dict__.keys():
            raise self.ConstError("Can't unbind const")
        raise NameError("名称不存在")
import sys
sys.modules[__name__] = _const()