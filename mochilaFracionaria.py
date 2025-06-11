# FUNÇÕES

def obterValorPeso(item):
	# Retorna o valor por peso do item
	return item["valor"] / item["peso"]

def mochilaFracionaria(itens, pesoMax):
	# Imprime a configuração de itens que possui o maior valor total, respeitando o peso máximo
	pesoRestante = pesoMax
	valorTotal = 0
	itensCp = itens.copy()
	
	# Ordena decrescentemente a cópia da lista de itens baseando-se no valor por peso de cada item
	itensCp.sort(key = obterValorPeso, reverse = True)
	
	print("Melhor configuração de itens para o peso máximo de {}: ".format(pesoMax))
	
	for item in itensCp:
		if (item["peso"] <= pesoRestante):
			valorTotal += item["valor"]
			pesoRestante -= item["peso"]
			
			print(" {} - Fracao: 1 | Valor: {} | Peso: {}".format(item["nome"], item["valor"], item["peso"]));

		elif (pesoRestante > 0):
			fracao = pesoRestante / item["peso"]
			valorTotal += item["valor"] * fracao
			
			print(" {} - Fracao: {:.2f} | Valor: {:.2f} | Peso: {:.2f} | Valor original: {} | Peso original: {}".format(item["nome"], fracao, item["valor"] * fracao, item["peso"] * fracao, item["valor"], item["peso"]));
			break;

	print("\nValor total: {:.2f}".format(valorTotal))

def imprimirItens(itens):
	# Imprime os dados dos itens
	print("Itens: ")
	
	for item in itens:
		print(" {} - Valor: {} | Peso: {} | Valor/peso: {:.2f}".format(item["nome"], item["valor"], item["peso"], item["valor"] / item["peso"]))


# PROGRAMA PRINCIPAL

itens = [
    {"nome": "item1", "valor": 11, "peso": 10},
    {"nome": "item2", "valor": 15, "peso": 12},
    {"nome": "item3", "valor": 7,  "peso": 6},
    {"nome": "item4", "valor": 8,  "peso": 5},
    {"nome": "item5", "valor": 6,  "peso": 4}
]

imprimirItens(itens)
print()
mochilaFracionaria(itens, 25)
