### eder:
a prova será prática, sem decorar

//deixar adm
su -

//ver interfaces
ip add
//(
  etc diretório de configurações;
  apt apontador de diretório
)
cd /etc/apt/sources.list.d

//editor de texto nano (abriu isso só pra mostrar, que aqui é onde ele busca fonte)
nano ubuntu.sources

apt install net-tools -y

//pra ver se funcionou
ifconfig

//rodar os seguintes bagulhos:
sudo modprobe dummy
sudo ip link add dummy0 type dummy
sudo ip link set dummy0 up
sudo ip addr add 192.168.0.1/24 dev dummy0

//ver se funcinou (tem q parecer dummy0):
ip addr
ou
ifconfig

### temp
o comando:
  sudo modprobe dummy
  sudo ip link add dummy0 type dummy
  sudo ip link set dummy0 up
  sudo ip addr add 192.168.0.1/24 dev dummy0

subistitui o nano do arquivo /etc/network/interfaces:

  auto enp0s8
  iface enp0s8 inet static
  address 192.168.0.1
  netmask 255.255.255.0
  network 192.168.0.0
  broadcast 192.168.0.255